package com.example.writhepdf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int STORAGE_CODE = 1000 ;
    EditText mTextEt,mTextDatosC,mTextoH,mTextEdu,mTextXP,mTextPro,mTextIdiomas;
    Button mSaveBtn;
    private Paragraph paragraph;
    private Font fTitle=new Font(Font.FontFamily.TIMES_ROMAN,30,Font.BOLD, BaseColor.ORANGE);
    private Font fSubTitle=new Font(Font.FontFamily.TIMES_ROMAN,18,Font.BOLD, BaseColor.ORANGE);
    private Font fText=new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);
    private Font fHighText=new Font(Font.FontFamily.TIMES_ROMAN,15,Font.BOLD);
    private  Document mDoc=new Document();
    private FloatingActionButton btnfloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextEt=findViewById(R.id.TextEd);
        mTextDatosC=findViewById(R.id.editText);
        mTextXP=findViewById(R.id.editText2);
        mTextEdu=findViewById(R.id.editText3);
        mTextoH=findViewById(R.id.editText4);
        mTextIdiomas=findViewById(R.id.editText5);
        mTextPro=findViewById(R.id.editText6);
        btnfloat=findViewById(R.id.btnFlotante);


        //mSaveBtn=findViewById(R.id.saveBtn);

        btnfloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M){

                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){

                        String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, STORAGE_CODE);

                    }else{
                        savePDF();
                    }
                }else{
                    savePDF();
                }
            }
        });

//        mSaveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
 //               if (Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
//
  //                  if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
//
  //                      String[] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //                    requestPermissions(permissions, STORAGE_CODE);
//
  //                  }else{
    //                    savePDF();
     //               }
       //         }else{
         //           savePDF();
          //      }

            //}
        //});
    }

    private void savePDF() {

        String mFileName=new SimpleDateFormat("yyyyMMYY_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());

        String mFilePath= Environment.getExternalStorageDirectory()+"/"+mFileName+".pdf";

        try {
            PdfWriter.getInstance(mDoc,new FileOutputStream(mFilePath));
            mDoc.open();
            String mText=mTextEt.getText().toString();
            String mText2=mTextDatosC.getText().toString();
            String mText3=mTextXP.getText().toString();
            String mText4=mTextEdu.getText().toString();
            String mText5=mTextoH.getText().toString();
            String mText6=mTextIdiomas.getText().toString();
            String mText7=mTextPro.getText().toString();

            mDoc.addAuthor("CV movil");
            mDoc.addTitle("CV"+"(Nombre)");
            addTitles(mText);
            addSubTitles("Informacion de Contacto");
            mDoc.add(new Paragraph(mText2));
            addSubTitles("Experiencia Laboral");
            mDoc.add(new Paragraph(mText3));
            addSubTitles("Educacion");
            mDoc.add(new Paragraph(mText4));
            addSubTitles("Habilidades");
            mDoc.add(new Paragraph(mText5));
            addSubTitles("Proyectos");
            mDoc.add(new Paragraph(mText6));
            mDoc.add(new Paragraph(mText7));

            mDoc.close();
            Toast.makeText(this, mFileName+".pdf\nSe guardó en\n"+mFilePath, Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    private void addChildP(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);

    }

    public void crearEncabez( String[] header,String anc) {
    try{
        PdfPTable pdfPTable=new PdfPTable(header.length);
        pdfPTable.setWidthPercentage(100);
        PdfPCell pdfPCell;
        pdfPCell=new PdfPCell(new Phrase(anc,fTitle));
        pdfPTable.addCell(pdfPCell);
        paragraph.add(pdfPTable);
        mDoc.add(new Paragraph(paragraph));
    }catch (Exception e){

    }


    }

    public void addTitles(String title){
        try{
            paragraph=new Paragraph();
            addChildP(new Paragraph(title,fTitle));
            paragraph.setSpacingAfter(30);
            mDoc.add(paragraph);
        }catch (Exception e){
            Log.e("addTitle",e.toString());
        }
    }

    public void addSubTitles(String subTitle){
        try{
            paragraph=new Paragraph();
            addChildP(new Paragraph(subTitle,fSubTitle));
            paragraph.setSpacingAfter(10);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            mDoc.add(paragraph);
        }catch (Exception e){
            Log.e("addTitle",e.toString());
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

            case STORAGE_CODE:{
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                    savePDF();

                }else{
                    Toast.makeText(this,"Permiso denegado",Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
