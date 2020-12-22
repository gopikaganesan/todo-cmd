import java.io.*;
import java.util.*;
import java.time.format.DateTimeFormatter; 
import java.time.LocalDateTime;
import java.lang.*;

public class Todo {

	public static List<String> list; 
	public static List<String> dlist;

	public static void load(List ls,String fl){
		try {
			File fr = new File(fl);
			Scanner sc = new Scanner(fr);
			while(sc.hasNextLine()) {
        		String data = sc.nextLine();
        		ls.add(data);
      			}
      		sc.close();
		} catch (FileNotFoundException e) {}
	
	}

	public static void save(List ls,String fl){
		try{    
           FileWriter fw = new FileWriter(fl); 
			for(int l=0;l< ls.size(); l++)   
				fw.write(ls.get(l)+"\n");       
           fw.close();    
          }catch(Exception e){
          } 
	}

	public Todo(){
		list = new ArrayList<String>();
		dlist = new ArrayList<String>(); 
        try {  
            File file = new File("todo.txt");  
            File file2 = new File("done.txt");
            load(list,"todo.txt");
            load(dlist,"done.txt");

        } catch (Exception e) { } 
				
	}

	public void listToDo(){
             if(list.size()==0){
            	System.out.print("There are no pending todos!");
            	}
            else{
           	for(int i=(list.size()-1); i>=0; i--)  {
           	    System.out.print("[" + (i+1)+"] "+ list.get(i)+"\n");  
           }  
          }
	}


	public void addToDo(String item){
		list.add(item);
		save(list,"todo.txt");
	}

	public void delToDo(int d){
		list.remove(d-1);
		save(list,"todo.txt");
	}

	public int delToDoS(String s){
		for(int i=0;i<list.size();i++){
			if(s.equals(list.get(i))){
				delToDo(i+1);
				return (i+1);
			}
		}
		return 0;
	}

	public void doneToDo(int d){
		dlist.add(list.get(d-1));
		save(dlist,"done.txt");
	}

	public void doneToDoS(String s){
		dlist.add(s);
		save(dlist,"done.txt");
	}


	public static void message(){
				System.out.print("Usage :-\n$ java Todo add \"todo item\"  # Add a new todo\n$ java Todo ls               # Show remaining todos\n$ java Todo del NUMBER       # Delete a todo\n$ java Todo done NUMBER      # Complete a todo\n$ java Todo help             # Show usage\n$ java Todo report           # Statistics");
		}

	public static void main(String args[]){
		Todo t = new Todo();
		if(args.length==0){
			message();
		}else{
		switch(args[0]){

			case "add":{
				if(args.length!=1){
					for(int i=1; i<args.length;i++){
					String array = args[i];
					t.addToDo(array);
					System.out.println("Added todo: \""+ array+"\"" );
					}
					
				} else{
				System.out.println("Error: Missing todo string. Nothing added!");

				}
	
			}
				break;
			case "ls":
				t.listToDo();
				break;
			case "del":{
				if(args.length!=1){
					for(int i=1; i<args.length;i++){
						String array = args[i];

							try{
							if(Integer.parseInt(args[i])>(args.length+1) || Integer.parseInt(args[i])<=0){
								System.out.print("Error: todo #"+Integer.parseInt(args[i])+" does not exist. Nothing deleted.");
							}
							else{
							t.delToDo(Integer.parseInt(args[i]));
							System.out.print("Deleted todo #"+ args[i]);
							}
						} catch(Exception e){
							int c = t.delToDoS(array);
							System.out.print("Deleted todo #"+ c);
						}

					}
				}
			else{
				System.out.println("Error: Missing NUMBER for deleting todo.");
			}
			}
			
				break;
			case "done":{
						if(args.length!=1){
							for(int i=1; i<args.length;i++){
								String s = args[i];
								try{
									if(Integer.parseInt(args[i])>args.length || Integer.parseInt(args[i])<=0){
										System.out.print("Error: todo #"+Integer.parseInt(args[i])+" does not exist.");
									}
										else{
											t.doneToDo(Integer.parseInt(args[i]));
											t.delToDo(Integer.parseInt(args[i]));
											System.out.print("Marked todo #"+ args[i]+" as done.");
											}
									} catch(Exception e){
										t.doneToDoS(s);
										int c = t.delToDoS(s);
										System.out.print("Marked todo #"+ c +" as done.");
									}
								}
							}
						else{
							System.out.print("Error: Missing NUMBER for marking todo as done.");
						}
	
					}
				break;
			case "help":{
				message();
			} break;
			case "report": {
				LocalDateTime now = LocalDateTime.now();  
				DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd"); 
				System.out.println(df.format(now)+" Pending : "+list.size()+" Completed : "+dlist.size());
			break;
		}
		default : {
			message();
			} break;
		}
	}
	}
}
