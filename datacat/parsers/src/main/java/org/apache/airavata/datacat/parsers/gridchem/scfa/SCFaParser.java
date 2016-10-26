/*
*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*
*/
package org.apache.airavata.datacat.parsers.gridchem.scfa;


import java_cup.runtime.lr_parser;
import org.apache.airavata.datacat.parsers.gridchem.GridChemQueueParser;

import javax.swing.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


public class SCFaParser extends java_cup.runtime.lr_parser implements GridChemQueueParser {

  /** Default constructor. */
  public SCFaParser() {super();}

  /** Constructor which sets the default scanner. */
  public SCFaParser(java_cup.runtime.Scanner s) {super(s);}

    /**
     * Constructor which uses a file reader.
     */
    public SCFaParser(final FileReader fileReader) {
        super(new SCFaLexer(fileReader));
    }


    /** Production table. */
  protected static final short _production_table[][] = 
    lr_parser.unpackFromStrings(new String[]{
            "\000\010\000\002\003\005\000\002\002\004\000\002\004" +
                    "\003\000\002\005\004\000\002\005\003\000\002\012\002" +
                    "\000\002\006\006\000\002\007\004"});

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    lr_parser.unpackFromStrings(new String[]{
            "\000\017\000\004\004\005\001\002\000\004\006\011\001" +
                    "\002\000\004\006\uffff\001\002\000\004\002\007\001\002" +
                    "\000\004\002\000\001\002\000\006\005\020\006\011\001" +
                    "\002\000\004\012\013\001\002\000\006\005\ufffd\006\ufffd" +
                    "\001\002\000\004\007\ufffc\001\002\000\004\007\015\001" +
                    "\002\000\004\013\017\001\002\000\006\005\ufffb\006\ufffb" +
                    "\001\002\000\006\005\ufffa\006\ufffa\001\002\000\004\002" +
                    "\001\001\002\000\006\005\ufffe\006\ufffe\001\002"});

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    lr_parser.unpackFromStrings(new String[]{
            "\000\017\000\006\003\005\004\003\001\001\000\006\005" +
                    "\007\006\011\001\001\000\002\001\001\000\002\001\001" +
                    "\000\002\001\001\000\004\006\020\001\001\000\002\001" +
                    "\001\000\002\001\001\000\004\012\013\001\001\000\004" +
                    "\007\015\001\001\000\002\001\001\000\002\001\001\000" +
                    "\002\001\001\000\002\001\001\000\002\001\001"});

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$parser$actions action_obj;

    //Each string is of the format
    //energy, iteration
    private ArrayList<String> result = new ArrayList<String>();
    private String tempStr = "";



    public ArrayList<String> getResult() {
        return result;
    }

    public void addToResult(String value) {
        result.add(value);
    }

    public String getTempStr() {
        return tempStr;
    }

    public void setTempStr(String s) {
        this.tempStr = s;
    }



  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$parser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$parser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}

    /*Adding the parsed data to the hash map */
   @Override
   public HashMap<String, String> getParsedData() throws Exception {

        parse();
        HashMap<String,String> results= new HashMap<String,String>();
        int energyCount=0;
        int iterationCount=0;
        for(int i=0;i<result.size();i++){
            String singleString = result.get(i);
            String[] temp= singleString.split(" ");
            if(temp.length>1){
                String keyString= temp[0];
                String dataString=temp[1];
                if(keyString.equalsIgnoreCase("ENERGY")){
                    results.put("SCFaParser_ENERGY_"+energyCount,dataString);
                    energyCount++;
                }else if(keyString.equalsIgnoreCase("ITERATION")){
                    results.put("SCFaParser_ITERATION_"+iterationCount,dataString);
                    iterationCount++;
                }

            }

        }
        return results;
    }
}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$parser$actions {

 
  //__________________________________
  public static boolean DEBUG = true;
  private static JTable table;               
  private static final String tableLabel = "SCF Intermediate Results:";
// private static String cycle = "0";
 
  
  public static JTable getTable() {
    return table;
  }

  public static String getTableLabel() {
    return tableLabel;
  }

//   }

  private final SCFaParser SCFaParser;

  /** Constructor */
  CUP$parser$actions(SCFaParser SCFaParser) {
    this.SCFaParser = SCFaParser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$parser$do_action(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$parser$result;

      /* select the action based on the action number */
      switch (CUP$parser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // cycle ::= Energ ENERGY 
            {
              Object RESULT = null;
		int enerleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).left;
		int enerright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).right;
		Float ener = (Float)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-0)).value;
		 //___________________________________________________________________
   if (DEBUG) System.out.println("CUP:scfa:  ENERGY "+ener);
    SCFaParser.addToResult("ENERGY "+ener);
 
              CUP$parser$result = new java_cup.runtime.Symbol(5/*cycle*/, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).right, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // scfcycle ::= NSearch ITERATION NT$0 cycle 
            {
              Object RESULT = null;
              // propagate RESULT from NT$0
              if ( ((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value != null )
                RESULT = (Object) ((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		int iterleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int iterright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		Integer iter = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;

              CUP$parser$result = new java_cup.runtime.Symbol(4/*scfcycle*/, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-3)).left, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).right, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // NT$0 ::= 
            {
              Object RESULT = null;
		int iterleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).left;
		int iterright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).right;
		Integer iter = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-0)).value;
 //___________________________________________________________________
   if (DEBUG) System.out.println("CUP:scfa: ITERATION  "+iter);
   SCFaParser.addToResult("ITERATION "+iter);
 
              CUP$parser$result = new java_cup.runtime.Symbol(8/*NT$0*/, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).right, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).right, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // scfpat ::= scfcycle 
            {
              Object RESULT = null;

              CUP$parser$result = new java_cup.runtime.Symbol(3/*scfpat*/, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).left, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).right, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // scfpat ::= scfpat scfcycle 
            {
              Object RESULT = null;
		 if (DEBUG) System.out.println("CUP:scfa: in scfpat"); 
              CUP$parser$result = new java_cup.runtime.Symbol(3/*scfpat*/, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).right, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // scfintro ::= FOUNDITER 
            {
              Object RESULT = null;
		 if (DEBUG) System.out.println("CUP:scfa:  found the start of Iteration"); 
              CUP$parser$result = new java_cup.runtime.Symbol(2/*scfintro*/, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).left, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).right, RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= startpt EOF 
            {
              Object RESULT = null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Object start_val = (Object)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		RESULT = start_val;
              CUP$parser$result = new java_cup.runtime.Symbol(0/*$START*/, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).right, RESULT);
            }
          /* ACCEPT */
          CUP$parser$parser.done_parsing();
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // startpt ::= scfintro scfpat SCFDONE 
            {
              Object RESULT = null;
		 if (DEBUG) System.out.println("CUP:gopt:  end of parse tree "); 
 	       table = new JTable();
	
//       table = parseSCF.getTable();
 	     
              CUP$parser$result = new java_cup.runtime.Symbol(1/*startpt*/, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-0)).right, RESULT);
            }
          return CUP$parser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}
