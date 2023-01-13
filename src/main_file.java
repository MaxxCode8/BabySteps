
import java.util.*;
import java.io.*;

public class main_file {
    public static void main(String[] args) throws IOException {

        // Creating a Registry for All the students in a class!
        HashMap<Integer,String> Registry = new HashMap<>();
        // creating a hashmap for processed leave applications...
        HashMap<Integer,String> processed_leave_reg = new HashMap<>();

        // <MAIN CONSOLE UI>                                          A loop for main user interface !
        int choice = 0;
        while(choice != 6){
            System.out.println();
            Scanner scan = new Scanner(System.in);
            System.out.println("""
                                           Welcome! to the LEAVE--MANAGEMENT sys!🤖
                                                1.Apply for a Leave.📃
                                                2.View Leave Status.📒
                                                3.Add New Student.👩👨
                                                4.__ADMIN LOGIN__🧔
                                                5.About.🤖
                                                6.___EXiT...🙏🔺""");
            choice = scan.nextInt();

            if (choice==1){
                //Calling helper function....
                ApplyLeave(Registry,processed_leave_reg);
            }
            if (choice==2){
                System.out.println("Enter your Roll Number: ");
                Integer roll = scan.nextInt();

                // Extracted processed applications txt file that has the status of their leave application!
                HashMap processed_apps = get_registry("Processed Leave List.txt");
                HashMap leave_apps = get_registry("Leave Applications.txt");

                if (processed_apps.containsKey(roll)){
                    if (Objects.equals(processed_apps.get(roll), "y")){
                        System.out.println("                           Category|Date|Explanation");
                        System.out.println("Your Leave Application>> "+leave_apps.get(roll));
                        System.out.println("Has been <<<✔✔✔APPROVED✔✔✔>>> by the respective Admin.");
                    }
                    if (Objects.equals(processed_apps.get(roll), "n")){
                        System.out.println("                           Category|Date|Explanation");
                        System.out.println("Your Leave Application>> "+leave_apps.get(roll));
                        System.out.println("Has been <<<❌❌❌DECLINED❌❌❌>>> by the respective Admin.\n" +
                                "                                                          🟥🔴Please Contact your Class-Teacher/Attendance Administrator!🟥🔴");
                    }
                } else if (leave_apps.containsKey(roll)) {
                    System.out.println("                           Category|Date|Explanation");
                    System.out.println("Your Leave Application>>> "+ leave_apps.get(roll)+"\n" +
                            "Is Still being Reviewed! <<< 🔺🔻PENDING🔻🔺>>> \n" +
                            "                                                          🟥🔴Please Contact your Class-Teacher/Attendance Administrator!🟥🔴");
                } else{
                    System.out.println("🟥There are NO Leave Applications applied for the ROll num>>>"+roll);
              }

            }
            if (choice==3){
                //Calling helper function....
                AddStudent(Registry);
            }
            if (choice==4){

                System.out.println("Enter your Login id: ");
                String loginid = scan.next();
                System.out.println("Enter your Password: ");
                String pass = scan.next();

                // Using the admin login class methods here ...
                boolean access = AdminLogin.checkLogin(loginid,pass);
                if (access){
                    System.out.println("______________________Admin Access Granted✔✔🧔");

                    int admin_choice = 0;
                    // looping through admin choices till admin dont exit ....
                    while(admin_choice!=4){
                        System.out.println();
                        System.out.println("""
                            Welcome! Admin
                               1.Prioritized Leave Applications List 🧾
                               2.Students Register 📓 (Sorted Roll number wise)
                               3.Add 🆕 Admin.
                               4.___EXiT Admin MODE___🔺""");
                        admin_choice = scan.nextInt();


                        if (admin_choice==1){
                            //PRINT out SORTED leave category applications ...

                            // Extracting the stored registry in txt file using helper function...
                            HashMap leave_register = get_registry("Leave Applications.txt");

                            //Declaring arraylists each for 4 reason indices!!!!...
                            ArrayList<String> one = new ArrayList<>();
                            ArrayList<String> two = new ArrayList<>();
                            ArrayList<String> three = new ArrayList<>();
                            ArrayList<String> four = new ArrayList<>();

                            for(Object key: leave_register.keySet()){
                                String data = (String) leave_register.get(key);
                                int reason_idx = Integer.parseInt(data.split("|")[0]);

                                // Adding the data to RESPECTIVE arraylist which are named according to priority index
                                if(reason_idx==1){one.add(key +" |"+data.split("|", 2)[1]);
                                }
                                if(reason_idx==2){two.add(key+" |"+data.split("|", 2)[1]);
                                }
                                if(reason_idx==3){three.add(key+" |"+data.split("|", 2)[1]);
                                }
                                if(reason_idx==4){four.add(key+" |"+data.split("|", 2)[1]);
                                }

                            }

                            // Creating a HashMap for storing <serial no, application> pairs!
                            HashMap<Integer, String> to_Grant_list = new HashMap<>();
                            //Add the strings to the hashmap
                            int serial = 1;
                            addStringsToMap(one, to_Grant_list, serial,"🔻EMERGENCY🔻");
                            serial += one.size();
                            addStringsToMap(two, to_Grant_list, serial,"😷💉💊HEALTH ISSUE💊💉😷");
                            serial += two.size();
                            addStringsToMap(three, to_Grant_list, serial,"🆕ExTRA CURRICULAR ACTIVITY🔽");
                            serial += three.size();
                            addStringsToMap(four, to_Grant_list, serial,"🤷OTHER🤷‍");


                            // calling helper function for enabling admin to grant permission to students!!!...
                            Grant_Leave_ui(to_Grant_list,leave_register,processed_leave_reg,two,three,four);
                            ////////////////////////////////////////////////////////////////////////////////////////
                            try (FileWriter fw = new FileWriter("Processed Leave List.txt")) {
                                // Iterate over the hashmap
                                for (HashMap.Entry<Integer,String> entry : processed_leave_reg.entrySet()) {
                                    // Get the key and value
                                    Integer key = Integer.parseInt(String.valueOf(entry.getKey()));
                                    String value = entry.getValue();

                                    // Write the key and value to the file, separated by a comma
                                    fw.write(key + ":" + value + "\n");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        //Proper printing out all the student data to console ...
                        if (admin_choice==2){

                            // Extracting the stored registry in txt file using helper function...
                            HashMap registry = get_registry("Register.txt");

                            //Sorting the roll numbers from our hashmap...

                            // We need to create and array of keys from our hashmap keySet!
                            Set set = registry.keySet();
                            Integer[] keys = (Integer[]) set.toArray(new Integer[registry.size()]);

                            // Using quick sort algorithm from Sortme class ...
                            keys = Sortme.quickSort(keys,0,keys.length-1);

                            // Storing the sorted sequence of students data into a linked list !........
                            LinkedList ll = new LinkedList();

                            // Filling up the new LinkedList with sorted keys from keys array!
                            for (Integer key: keys){
                                String value = (String) registry.get(key);
                                // append is a method that is customized to add the new data to <END> of the linked list !
                                ll.append(Integer.toString(key)+ '|' + value);
                            }
                            // Printing out the Sorted Registry !
                            System.out.println("Roll number| GrNum | Name | Div/Year");
                            ll.peek(); // Prints out the entire sorted registry of students !

                        }
                        // This blocks add new login id and password to the login txt file...
                        if (admin_choice==3){
                            System.out.println("Enter NEW login id:");
                            String newloginid = scan.next();
                            System.out.println("Enter NEW password:");
                            String newpass = scan.next();
                            AdminLogin.storeLogin(newloginid,newpass);
                        }
                    }
                }
                else{System.out.println("____________________🔴🔴🔴 Admin Login Failed 🔴🔴🔴" +
                        "\nTry Again with correct credentials ///");
                }
            }
            if (choice==5){
                System.out.println("""
                                                                                  ___ABOUT___
                                           Future_Scope: 1>Unable to process "multiple leave requests" from a single student...
                                                            (This can be included by creating a separate database table for every student)
                                                         2>Image Processing can be used to directly get the details from a leave soft copy application...
                                                         3>Proper User Friendly Graphical User Interface... (GUi)
                                                            (currently, console user interface has been designed which is practically not user friendly)
                                                         4>DBMS can be used instead of creating a txt file and then again extracting data from it...
                                                            (Query language like mySQL, mongoDB,etc can be used for seamless data base access)
                                                         
                                           DSA_used:  1> HashMap____<Seamless access to individual student data and storing in a text file>
                                                      2> Linked List____<Printing out in ordered manner>
                                                      3> Array List____<Storing data to use .get() method for quick access>
                                                      4> Quick Sort (Sorting Algorithm)____<Best Time Complexity>
                                                       
                                           Lessons Learned: 1>Importance of << SCOPE >> in a CODE!
                                                            2>Basics of Debugging code!
                                                            3>Basics of Java Syntax...
                                                            4>Idea about Big Picture (Top Down approach) Software Development!
                                                            
                                                           /// Solving Problems is Fun!!! ///
                                                   
                                           To get your idea manifest into reality through code is really inspiring!!!🙏""");
            }
        }
        System.out.println();
        System.out.println("Much Sunshine, Love, Laughter in your Life ☺ \n" +
                "                        🙏 Thank you for using our Leave Management System! 🙏");

    }
    // helper function that adds a new student to our database!...
    public static void AddStudent(HashMap registry) throws IOException{

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter Your Roll call (e.g 44): ");
        Integer roll = Integer.parseInt(scan.nextLine());
        System.out.println("Enter Your PRN (e.g 12345678): ");
        String prn = scan.nextLine();
        System.out.println("Enter Your Name(e.g Elon_Musk): ");
        String name = scan.nextLine();
        System.out.println("Enter Your Div+Eng_Year(e.g ET-D|SY): ");
        String div_year = scan.nextLine();
        // Registry format for storing the info of new student
        registry.put(roll,prn+'|'+name+'|'+div_year);
        // message to show the process is completed successfully
        System.out.println("✔✔✔Student Registered Successfully!✔✔✔");

        // adding the data to a txt file for storing the data
        FileWriter writer = new FileWriter("Register.txt",true);
        writer.write(roll + ":" + registry.get(roll)+"\n");
        writer.close();

    }

    // helper function that extracts registry from stored text file !...
    public static HashMap get_registry(String filepath) throws IOException{
        //This functions return a HASHMAP which contains the extracted registry of the added students!!!!!!!!!!!!!!!

        // creating a hashmap that will be our EXTRACTED students registry !
        HashMap<Integer,String> registry = new HashMap<>();
        // below block creates a buffer reader instance that will scan through our txt file !
        String line;
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        // we will be traversing through the txt file until we hit a null line !
        while((line = reader.readLine()) != null){
            // Splitting the line based on : so we get a key and value
            String[] parts = line.split(":",2);
            if(parts.length >= 2){
                String key = parts[0];
                String value = parts[1];
                // putting the key value pair in our hashmap! registry
                registry.put(Integer.parseInt(key),value);
            } else {
//                System.out.println("ignoring line:"+line);
                System.out.println(line);
            }
        }
        reader.close();
        return registry;
    }

    // helper method that carries out storing the leave application ...
    public static void ApplyLeave(HashMap leave_app_reg,HashMap processed_leave_list) throws IOException{

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter Your Roll num: ");
        String roll = scan.nextLine();

        // Extracting the Main Registry of students so we can let VALID students to apply for a leave
        // else please register prompt will appear...
        HashMap registry = get_registry("Register.txt");

        // Below condition checks whether the roll number entered is valid or not ...
        if (registry.containsKey(Integer.parseInt(roll))){
            System.out.println("""
                                Category of your Leave:
                                  1)Emergency‼‼
                                  2)Health Issue 😷
                                  3)Extra Curricular Activity 🆕
                                  4)Other 🙋‍️🙋
                                Please enter suitable Integer:""");

            // reason index will be basically used for determining the priority of the leave !
            // Highest priority for 1 and it declines over to 4
            String reason_idx = scan.nextLine();
            System.out.println("Date of your leave!\n (please strictly provide in this format dd/mm/yyyy):");
            String date = scan.nextLine();
            System.out.println("Brief Explanation of your leave: ");
            String explain = scan.nextLine();
            if (Integer.parseInt(reason_idx)==1){
                // ONly if the application falls under Category 1 that is Emergency!
                // ................................................then its automatically granted "y" for the leave here
                processed_leave_list.put(roll, "y");
                FileWriter fw = new FileWriter("Processed Leave List.txt");
                fw.write(roll + ":" + "y" + "\n");
                fw.close();
            }
            leave_app_reg.put(roll,reason_idx+'|'+ date +'|'+ explain);
            System.out.println("        ✔✔✔📄Leave Application submitted successfully!📄✔✔✔");
            System.out.println();


            // adding the data to a txt file for storing the data
            FileWriter writer = new FileWriter("Leave Applications.txt",true);
            writer.write(roll + ":" + leave_app_reg.get(roll)+"\n");
            writer.close();
        }else {
            System.out.println("_____This Roll number is not yet Registered in Student Database! {📄REGISTER📄}_____\n" +
                    "Please press << 3 >> to Register new student!");
        }
    }

    // helper method that adds the strings in a list to a map with a serial number as the key ...
    public static void addStringsToMap(ArrayList<String> priority_list, HashMap<Integer, String> hashmap, int serial, String listName) {
        // Print the name of the list
        System.out.println("-------- " + listName + " ---");
        System.out.println();
        // Add the strings to the map
        for (int i = 0; i < priority_list.size(); i++) {
            hashmap.put(serial + i, priority_list.get(i));
            System.out.println(serial + i + ": " + priority_list.get(i));
        }
        System.out.println();
    }

    // helper method that contains all the user interface required for Admin block to process leave applications ...
    public static void Grant_Leave_ui(HashMap grant_list, HashMap leave_register,HashMap processed_leave_reg,ArrayList two,ArrayList three,ArrayList four) throws IOException {

        // <GrantLeave CONSOLE UI>
        int choice = 0;
        while (choice != 5) {
            System.out.println();
            Scanner scan = new Scanner(System.in);
            System.out.println("""
                    Select action with suitable integer:
                         1.Grant leave for a specific serial number.🔢
                         2.Grant leave for an entire Category.📒
                         3.Grant LEAVE to ALL.👩👨
                         4.🔻DECLINE LEAVE for ALL.🙂
                         5.Return to Main Menu🟥⏹""");
            choice = scan.nextInt();

            if (choice == 1) {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Enter serial number: ");
                // srno is our serial number storing variable ...
                Integer srno = scanner.nextInt();
                System.out.println("🔴By Entering next input\nYou are GRANTING Permission " +
                        ",thus\nChanging the Leave Status of that student application!🔴");
                System.out.print("Enter y or n: ");
                // input stores "y" or "n" ...
                String input = scanner.next();

                /*    grant list == <srno : data(roll)>
                      leave reg  == <roll : data>            */

                //Extracting roll number from stored data in grant_list!
                String strRoll = (String) grant_list.get(srno);
                Integer roll = Integer.parseInt(strRoll.split(" ", 2)[0]);

                // updating the processed leave app list.txt with approval status in it !
                processed_leave_reg.put(roll, input);

                // NOW leave_register contains processed application results!

                // deletion of processed student data from leave-register is not done !
            }
            if (choice == 2) {
                // Here update the processed_leave_reg hashmap for ALL students in selected category!
                System.out.println("Which Category you want to grant permission?\n" +
                        "1.HEALTH ISSUE💊💉😷\n" +
                        "2.ExTRA CURRICULAR ACTIVITY🔽\n" +
                        "3.OTHER🤷\n" +
                        "                                   NOTE: 🔻EMERGENCY🔻 category is granted leave automatically!");
                int cat = scan.nextInt();

                if (cat == 1) {
                    for (Object item : two) {
                        Integer roll = Integer.parseInt(item.toString().split(" ", 2)[0]);
                        processed_leave_reg.put(roll, "y");
                    }
                }
                if (cat == 2) {
                    for (Object item : three) {
                        Integer roll = Integer.parseInt(item.toString().split(" ", 2)[0]);
                        processed_leave_reg.put(roll, "y");
                    }
                }
                if (cat == 3) {
                    for (Object item : four) {
                        Integer roll = Integer.parseInt(item.toString().split(" ", 2)[0]);
                        processed_leave_reg.put(roll, "y");
                    }
                }
                System.out.println("____✔✔✔Leave Granted for all applications in Category>>"+cat+"🧾✔✔✔_____");
            }
            if (choice == 3) {
                // We will merge all the category arraylist we have...
                ArrayList<String> mergedCatList = new ArrayList<>();
                mergedCatList.addAll(two);
                mergedCatList.addAll(three);
                mergedCatList.addAll(four);

                // now we can traverse through this merged Cat List and extract roll numbers
                // and update the processed leave list !
                for (String data : mergedCatList) {
                    Integer roll = Integer.parseInt(data.split(" ", 2)[0]);
                    processed_leave_reg.put(roll, "y");
                }System.out.println("_____🧾✔✔✔ Granted Leave for ALl Applications ✔✔✔🧾_____");
            }
            if (choice == 4) {
                //SIMILAR to upper choice 3 we just change "y" to "n"<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<!!!!!!!!
                // We will merge all the category arraylist we have...
                ArrayList<String> mergedCatList = new ArrayList<>();
                mergedCatList.addAll(two);
                mergedCatList.addAll(three);
                mergedCatList.addAll(four);

                // now we can traverse through this merged Cat List and extract roll numbers
                // and update the processed leave list !
                for (String data : mergedCatList) {
                    Integer roll = Integer.parseInt(data.split(" ", 2)[0]);
                    processed_leave_reg.put(roll, "n"); //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

                }System.out.println("_____🧾✔✔✔ DECLINED❌ Leave for ALl Applications ✔✔✔🧾_____");
            }
        }
    }

}
