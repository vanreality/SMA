package autre;

import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.LineChartSeries;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTMarkerStyle;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DocExcel {
	
	public String nom;
	public XSSFWorkbook workbook;
	
	public DocExcel(String nom) {
		
		this.nom=nom;
		this.workbook = new XSSFWorkbook();
	}
	
	public void ajouterOnglet(String nomOnglet, int[] valeurs, long tempsEx) {
		
		/************ CREATION ONGLET EXCEL ****************/

		
		this.workbook.createSheet(nomOnglet);
		for (int i=0;i<=valeurs.length;i++) {
			this.workbook.getSheet(nomOnglet).createRow(i);
		}
		
		/************ AJOUT DES VALEURS **********************/
		
		this.ajouterString(nomOnglet, "Essais", 0, 0);
		this.ajouterString(nomOnglet, "Résultats", 0, 1);
		this.ajouterString(nomOnglet, "TempsEx", 21, 4);

		
		for (int i=1; i<=valeurs.length;i++) {
			
			this.ajouterInt(nomOnglet, i, i, 0);
			this.ajouterInt(nomOnglet, valeurs[i-1], i, 1);
		}
				
		/************ CREATION DU GRAPHE **********************/

		this.traitementDonnees(nomOnglet, valeurs.length);

		/************ AJOUT DES VALEURS TEMPSEX, MIN, MAX, MOYENNE ETC****************/
		
		this.ajouterLong(nomOnglet, tempsEx, 22, 4);
		
		this.ajouterString(nomOnglet, "MIN",21,5);
		Cell cellMin=this.workbook.getSheet(nomOnglet).getRow(22).createCell(5, CellType.FORMULA);
		cellMin.setCellFormula("MIN(B2:B"+(int)(valeurs.length+1)+")");

		this.ajouterString(nomOnglet, "MAX",21,6);
		Cell cellMax=this.workbook.getSheet(nomOnglet).getRow(22).createCell(6, CellType.FORMULA);
		cellMax.setCellFormula("MAX(B2:B"+(int)(valeurs.length+1)+")");
		
		
		this.ajouterString(nomOnglet, "MOYENNE",21,7);
		Cell cellMoy=this.workbook.getSheet(nomOnglet).getRow(22).createCell(7, CellType.FORMULA);
		cellMoy.setCellFormula("MOYENNE(B2:B"+(int)(valeurs.length+1)+")");
		
		this.ajouterString(nomOnglet, "ECART_TYPE",21,8);
		Cell cellEcartType=this.workbook.getSheet(nomOnglet).getRow(22).createCell(8, CellType.FORMULA);
		cellEcartType.setCellFormula("ECARTYPE.STANDARD(B2:B"+(int)(valeurs.length+1)+")");

		/************ CREATION DU DOC EXCEL ****************/

		this.creerDocExcel();
	}
		
	public void ajouterInt(String nomOnglet,int valeur,int ligne, int colonne) {
		this.workbook.getSheet(nomOnglet).getRow(ligne).createCell(colonne).setCellValue(valeur);		
	}
	
	public void ajouterLong(String nomOnglet,long valeur,int ligne, int colonne) {
		this.workbook.getSheet(nomOnglet).getRow(ligne).createCell(colonne).setCellValue(valeur);		
	}
	
	public void ajouterString(String nomOnglet,String valeur,int ligne, int colonne) {
		this.workbook.getSheet(nomOnglet).getRow(ligne).createCell(colonne).setCellValue(valeur);		
	}
	
	public void traitementDonnees(String nomOnglet, int nombreValeurs) {
		

			/************************GRAPHE**************************/
			
			XSSFDrawing drawing = this.workbook.getSheet(nomOnglet).createDrawingPatriarch();
			ClientAnchor anchor = drawing.createAnchor(0, 0, 0,0,3,2,20,20);
			
			XSSFChart chart= drawing.createChart(anchor);

	        LineChartData data = chart.getChartDataFactory().createLineChartData();

	        ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
	        ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
	        leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

	        ChartDataSource<Number> xs = DataSources.fromNumericCellRange(this.workbook.getSheet(nomOnglet), new CellRangeAddress(1,nombreValeurs,0,0));
	        ChartDataSource<Number> ys = DataSources.fromNumericCellRange(this.workbook.getSheet(nomOnglet), new CellRangeAddress(1,nombreValeurs,1,1));

	        LineChartSeries series1 = data.addSeries(xs, ys);

	        chart.plot(data, bottomAxis, leftAxis);
	        
	        }
			
	public void creerDocExcel() {
		try {
			FileOutputStream output = new FileOutputStream(this.nom);
			this.workbook.write(output);
			output.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
