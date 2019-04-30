package autre;

import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.LineChartSeries;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;


public class BaseDeDonneesIA {

	public FileInputStream file;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	
	public BaseDeDonneesIA(String nomFichierDepart) {
		
		try {
			this.file= new FileInputStream(new File("./donnees/"+nomFichierDepart+".xlsx"));
	        this.workbook = new XSSFWorkbook(file);
	        this.sheet= workbook.getSheetAt(1);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	       
	public void remplirBaseDeDonneesIA(String nomFichierFin,int ligneDebut, int ligneFin,int nombreDeTestsParLigne) throws IOException {
		
		outil_Tabou.Donnees donnees=new outil_Tabou.Donnees(); // Création de la base de donnée des villes

		int t=0;
		
		for (int i=ligneDebut-1;i<ligneFin;i++) {
			
			/*********** récupération des données sur la ligne ***************************/
			
			int taillePVC=0;
			String methode="";
			int param1,param2,param3=0;
			
	        taillePVC=(int)this.sheet.getRow(i).getCell(0).getNumericCellValue();	        
	        methode=this.sheet.getRow(i).getCell(1).getStringCellValue();
	        param1=(int)this.sheet.getRow(i).getCell(2).getNumericCellValue();
	        param2=(int)this.sheet.getRow(i).getCell(3).getNumericCellValue();
	        param3=(int)this.sheet.getRow(i).getCell(4).getNumericCellValue();
	        
			/************traitement des données et calculs pour TABOU**********************************/
	        
	        outil_Tabou.Voyage Voyage=new outil_Tabou.Voyage(donnees,taillePVC); // Création du voyage courant
	        			
	        if (methode.equals("Tabou")) {
	        
	    		int [] tableauValeursTabou= new int [nombreDeTestsParLigne];
	    		long debut=System.currentTimeMillis();
	    		
	    		for (int k=0; k<nombreDeTestsParLigne;k++) {
	    			outil_Tabou.Ordonnancement ordo= new outil_Tabou.Ordonnancement(Voyage.villesOrdonneesParId, Voyage);
	    			tableauValeursTabou[k]=ordo.Tabou2(param1, param2, param3).calculCout();
	    		}
	    		
	    		long temps_execution= (System.currentTimeMillis()-debut) /nombreDeTestsParLigne;
	    		int min_fitness=this.calculMin(tableauValeursTabou);
	    		int max_fitness=this.calculMax(tableauValeursTabou);
	    	    int ecart=max_fitness-min_fitness;
	    		int moyenne = this.calculMoy(tableauValeursTabou);
	    		
	    		this.sheet.getRow(i).createCell(5).setCellValue(min_fitness);
	    		this.sheet.getRow(i).createCell(6).setCellValue(ecart);
	    		this.sheet.getRow(i).createCell(7).setCellValue(moyenne);
	    		this.sheet.getRow(i).createCell(8).setCellValue(temps_execution);

	        }
	        System.out.println(t++);
		}	
		
		FileOutputStream output = new FileOutputStream("./donnees/"+nomFichierFin+".xlsx");
		this.workbook.write(output);
		output.close();
	}
	
        
	/********************** OUTILS ********************************/

	
	public int calculMin(int[] tableau) {
		
		int min=tableau[0];
		
		for (int i=1;i<tableau.length;i++) {
			if (tableau[i]<min) {
				min=tableau[i];
			}
		}
		return min;
	}
	
	public int calculMax(int[] tableau) {
		
		int max=tableau[0];
		
		for (int i=1;i<tableau.length;i++) {
			if (tableau[i]>max) {
				max=tableau[i];
			}
		}
		return max;
	}
	
	public int calculMoy(int[] tableau) {
		
		int s=0;
		
		for (int i=0;i<tableau.length;i++) {
			s+=tableau[i];
		}
		return s/tableau.length;
	}
	
	public static void main(String[] args) throws IOException {
		
		BaseDeDonneesIA b=new BaseDeDonneesIA("BaseDeDonneesIAModifiee");
		
		b.remplirBaseDeDonneesIA("BaseDeDonneesIAModifiee",1013,1103,10);
		
	}
	
 
}
