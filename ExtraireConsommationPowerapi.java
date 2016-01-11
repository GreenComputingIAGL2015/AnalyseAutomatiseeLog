import java.util.Scanner;
import java.util.LinkedList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.lang.Math;

public class ExtraireConsommationPowerapi {
	private String chemin;
	private int ecartMS;
	private boolean lissage;
	
	public ExtraireConsommationPowerapi(String chemin, boolean lissage)  {
		this.chemin = chemin;
		this.lissage = lissage;
	}
	
	private long extraireTimestamp(String s) {
		return Long.parseLong(s.split(";")[1].split("=")[1]);
	}
	private double extrairePower(String s) {
		return Double.parseDouble(s.split(";")[4].split("=")[1].split(" ")[0]);
	}
	public void extraireConsommation() {
		try {
			FileReader fr = new FileReader(chemin);
			BufferedReader br = new BufferedReader(fr);
			String s;
			
			double d = 0;
			
			double msCourant = 0;
		
			int nb = 0;
		
			int sTotal = 0;
		
			double consommationTotale = 0;
			
			double mW = 0;
			double mWCourant = 0;
			
			double temp = 0;
			
			while ((s = br.readLine()) != null) {
				if (d == 0) {
					d = extraireTimestamp(s);
				}
				msCourant = extraireTimestamp(s);
				mWCourant = extrairePower(s);
				mW = mW + mWCourant;
				if  ((!lissage) || (lissage && (mWCourant > 0))) {
					nb = nb +1;
				}
				if ((msCourant - d) >= 1000) {
					sTotal = sTotal +1;
					
					temp = mW/nb*Math.pow(10,-3);
					System.out.println(sTotal  + ";" +temp);
					consommationTotale = consommationTotale + temp;
					
					mW = 0;
					nb = 0;
					d = 0;
				}
			}
			System.out.println("Consommation totale;" + consommationTotale);
			br.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String args[]) {
		if (args.length == 0) {
			System.out.println("Erreur, au moins un argument attendu !");
		} else {
			if (args.length == 1) {
				ExtraireConsommationPowerapi ecp =  new ExtraireConsommationPowerapi(args[0],false);
				ecp.extraireConsommation();
			}
			else {
				ExtraireConsommationPowerapi ecp =  new ExtraireConsommationPowerapi(args[0],args[1].equals("true"));
				ecp.extraireConsommation();
			}
		}
	}
}
