import java.lang.reflect.Array;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by kevin on 29/05/2017.
 */
public class algo {

    private static final String FILENAME = "gr0.California.txt";

    public static List dataFiles = new ArrayList();

    private static void readfile(){

        BufferedReader br=null;
        FileReader fr=null;

        try {

            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;

            br = new BufferedReader(new FileReader(FILENAME));

            while ((sCurrentLine = br.readLine()) != null) {

                String[] tab = sCurrentLine.split("\\s+");
                DataFile data = new DataFile(tab[0],Integer.parseInt(tab[1]),tab[2]);

                dataFiles.add(data);
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }



    public void HITS(Map graph){

        for(int i=0; i < graph.size(); i++){

            Object variable = graph.get(i);


        }


    }


    public static void main(String [] args){

        readfile();


    }
}



