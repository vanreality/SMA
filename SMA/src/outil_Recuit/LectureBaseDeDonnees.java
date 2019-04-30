package outil_Recuit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class LectureBaseDeDonnees {
	
	String fichier;
	ArrayList<Parametres> matriceDesParametres = new ArrayList<Parametres>();
	
	public LectureBaseDeDonnees(String fichier) {
		
		this.fichier = fichier;
	}
	
	public ArrayList<Parametres> récupérerParamètres() {
		File file = new File (this.fichier);
		try {
			Workbook workbook = WorkbookFactory.create(file);
			Sheet sheet = workbook.getSheet("Feuil1");
			
			int indiceDeLigne = 3; // C'est ma première ligne
			Row row = sheet.getRow(indiceDeLigne);
			
			while(indiceDeLigne<663){
				
				int nbVilles = Math.toIntExact(Math.round(row.getCell(0).getNumericCellValue())) ;
				int param1 = Math.toIntExact(Math.round(row.getCell(2).getNumericCellValue())) ;
				double param2 = row.getCell(3).getNumericCellValue() ;
				double param3 = row.getCell(4).getNumericCellValue() ;
				
				this.matriceDesParametres.add(new Parametres(nbVilles, param1, param2, param3));
				
				indiceDeLigne += 1;
				row = sheet.getRow(indiceDeLigne);
				workbook.close();
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		return this.matriceDesParametres;		
	}
	
	public void ecrireResultats(double[][] matriceDesResultats) {
		FileOutputStream fileout;
		
		File file = new File(this.fichier);
		try {
			Workbook workbook = WorkbookFactory.create(file);

			file.renameTo(new File(this.fichier+"temp.xlsx"));
			Sheet sheet = workbook.getSheet("Feuil1");
			
			int indiceDeLigne = 3; // C'est ma première ligne
			Row row = sheet.getRow(indiceDeLigne);
			
			while(indiceDeLigne<663){
				for(int i = 0; i<4; i++) {
					if(row.getCell(i+5) != null) {
						row.removeCell(row.getCell(i+5));
					} 
					row.createCell(i+5).setCellValue(matriceDesResultats[indiceDeLigne-3][i]);
					
				}
				
				indiceDeLigne += 1;
				row = sheet.getRow(indiceDeLigne);
			}

			fileout = new FileOutputStream(this.fichier);
			workbook.write(fileout);
			fileout.close();
			Files.delete(Paths.get(this.fichier+"temp.xlsx"));
			
			System.out.println("Ecriture finie");
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	
}
