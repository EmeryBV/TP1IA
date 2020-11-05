package csp_etud;

import java.io.BufferedReader;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ConstraintExp extends Constraint {

	
	private String expression; 
	protected static ScriptEngineManager mgr = new ScriptEngineManager();
    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
    
    ConstraintExp(BufferedReader in) throws Exception {
		super(in);
		expression = in.readLine().trim();
	}

	private static void enginePut (ScriptEngine e, String var, Object value) {
		try {
			e.put(var, Integer.parseInt(value.toString()));
		} catch (NumberFormatException nfe) {
			try {
				e.put(var, Float.parseFloat(value.toString()));
			} catch (NumberFormatException nfe2) {
				if (value.toString() == "false" || value.toString() == "true") {
					e.put(var, Boolean.parseBoolean(value.toString()));
				} else {
					e.put(var,  value);
				}
			}
		}
	}

	

	@Override
	public boolean violation(Assignment a) {

		for(String var : this.getVars()){
			if(!(a.getVars().contains(var))) return false;
		}


		for(String var : this.getVars()){
			enginePut(engine,var,a.get(var));
		}

		try {

			return !((boolean)engine.eval(expression));
		}
		catch (ScriptException e)
		{ System.err.println("probleme dans: "+ expression); }

		//System.out.println(assigned_expression);
		return true;
	}

	@Override
	public boolean violationOpt(Assignment a) {
		return violation(a) ;
	}

	@Override
	public String toString() {
		
		return "Contrainte de type Exp" + super.toString();
	}

}

	
	

