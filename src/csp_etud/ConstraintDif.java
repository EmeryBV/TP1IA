package csp_etud;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class ConstraintDif extends Constraint {

	
	public ConstraintDif(BufferedReader in) throws Exception {
		super(in);
	
	}




	@Override
	public boolean violation(Assignment a) {
		boolean result = false;
		for (int i = 0; i < varList.size(); i++) {
			String key1 = varList.get(i);
			if (a.containsKey(key1)) {
				for (int j = i + 1; j < varList.size(); j++) {
					String key2 = varList.get(j);
					if (a.containsKey(key2) && a.get(key1).equals(a.get(key2))) {
						result= true;
					}
				}
			}
		}
		return result;
	}

	@Override
	public boolean violationOpt(Assignment a) {
		for (int i = 0; i < varList.size(); i++) {
			String key1 = varList.get(i);
			if (a.containsKey(key1)) {
				for (int j = i + 1; j < varList.size(); j++) {
					String key2 = varList.get(j);
					if (a.containsKey(key2) && a.get(key1).equals(a.get(key2))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		
		return "Contrainte de type DIF"+super.toString();
	}
}
