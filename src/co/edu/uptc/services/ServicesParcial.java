package src.co.edu.uptc.services;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import src.co.edu.uptc.models.Person;

public class ServicesParcial {
    
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private File file;
    private String charsetName;
    private String path;
    private List<Person> people;


    public ServicesParcial(){
        this.charsetName="UTF-8";
        people= new ArrayList<Person>();
    }

    public void setPath(String path){
        this.path=path;
    }
    private void openFile() throws Exception{
        file= new File(path);
        if (!file.exists()) {
            throw new Exception("El archivo no existe");
        }else{
            Reader reader = new InputStreamReader(new FileInputStream(file),this.charsetName);
            bufferedReader = new BufferedReader(reader);
        }
        
    }

    private ArrayList<String> readFile() throws Exception{
        String cont="";
        ArrayList<String> list= new ArrayList<String>();
        while ((cont=bufferedReader.readLine())!=null) {
            list.add(cont+"\n");
        }
        return list;
    }

    private void closedFile() throws Exception{
        if (bufferedReader!= null) {
            bufferedReader.close();
        }
    }

    private List<String> extractStrings() throws Exception{
        this.openFile();
        List<String> list=this.readFile();
        this.closedFile();
        return list;
    }

    private void separateString(List<String> list){
        String word="";
        Person person1= new Person();
        for (String string1 : list) {
            int count=0;
            int countData=0;
            int countFinaly=10;
            for (int i = 0; i < string1.length(); i++) {
                    switch (countData) {
                        case 0:
                        countFinaly=10;
                        if (count>=countFinaly) {
                            countData=countData+1;
                            person1.setAge(Integer.parseInt(word));
                            word=string1.substring(i, i+1);
                            count=0;
                        }else{
                            if (string1.substring(i, i+1).compareTo(" ")!=0) {
                                if (string1.substring(i, i+1).compareTo("\n")!=0) {
                                    word=word+string1.substring(i, i+1);
                                }
                            }
                        }
                        break;
                        
                        case 1:
                        countFinaly=40;
                        if (count>=countFinaly) {
                            countData=countData+1;
                            person1.setName(word);;
                            word=string1.substring(i, i+1);
                            count=0;
                        }else{
                            if (string1.substring(i, i+1).compareTo(" ")!=0) {
                                if (string1.substring(i, i+1).compareTo("\n")!=0) {
                                    word=word+string1.substring(i, i+1);
                                }
                            }
                        }
                        break;
        
                        case 2:
                        countFinaly=40;
                        if (count>=countFinaly) {
                            countData=countData+1;
                            person1.setLastname(word);;
                            word="";
                            count=0;
                        }else{
                            if (string1.substring(i, i+1).compareTo(" ")!=0) {
                                if (string1.substring(i, i+1).compareTo("\n")!=0) {
                                    word=word+string1.substring(i, i+1);
                                }
                            }
                        }
                        break;
                        
                        case 3:
                        countFinaly=20;
                        if (count>=countFinaly) {
                            countData=0;
                            person1.setSalary(Integer.parseInt(word));
                            people.add(person1);
                            person1 = new Person();
                            word="";
                            count=0;
                        }else{
                            if (string1.substring(i, i+1).compareTo(" ")!=0) {
                                if (string1.substring(i, i+1).compareTo("\n")!=0) {
                                    word=word+string1.substring(i, i+1);
                                }
                            }
                        }
                        break;
        
                        default:
                            break;
                    }
                    count=count+1;
            }
        }
    }

    public void ready() throws Exception{
        separateString(extractStrings());
        writeMayor();
        writeMenor();
    }

    private void writeMayor() throws IOException{
        actuFile(".\\MayorSalario.txt");
        createWrite();
        writeFileCombined(organiceMayorSalary());
        closeWrite();
    }

    private void writeMenor() throws IOException{
        actuFile(".\\MenorSalario.txt");
        createWrite();
        writeFileCombined(organiceMenorSalary());
        closeWrite();
    }

    private String organiceMayorSalary(){
        int promSalary=mayorSalary();
        String person="";
        for (Person person1 : people) {
            if (person1.getSalary()>=promSalary) {
                person=person+person1.getName()+"%"+person1.getLastname()+"%"+person1.getSalary()+"%"+person1.getAge()+"%"+"\n";
            }
        }
        return person;
    }

    private String organiceMenorSalary(){
        int promSalary=mayorSalary();
        String person="";
        for (Person person1 : people) {
            if (person1.getSalary()<promSalary) {
                person=person+person1.getName()+"###"+person1.getLastname()+"###"+person1.getSalary()+"###"+person1.getAge()+"###"+"\n";
            }
        }
        return person;
    }

    private int mayorSalary(){
        int salaryProm=0;
        int count=0;
        for (Person person : people) {
            salaryProm=salaryProm+person.getSalary();
            count=count+1;
        }
        salaryProm=salaryProm/count;
        salaryProm=Math.abs(salaryProm);
        return salaryProm;
    }

    private void actuFile(String path){
        file= new File(path);
    }
    private void createWrite() throws IOException{
        fileWriter= new FileWriter(file);
        bufferedWriter= new BufferedWriter(fileWriter);
    }

    private void writeFileCombined(String contended) throws IOException{
        bufferedWriter.write(contended);
    }

    private void closeWrite() throws IOException{
        bufferedWriter.close();
    }
}
