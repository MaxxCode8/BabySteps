//notes...
/* so when we are creating a class.<-- function we need public <VOID>
and when we need a return at the end we use static<---
 */

import java.util.*;
import java.io.*;

public class LinkedList {

    public static void main(String[] args) {
        System.out.println("This Class contains Linked List!\n");
        LinkedList list = new LinkedList();
        list.push("23");
//        list.append(23);
//        list.push(9);
//        list.append(45);
//        list.push(9);
        System.out.println();
        list.peek();
        System.out.println(list.pop());

        list.pop();

        list.pop();
        list.peek();
        System.out.println();
        System.out.println("Successfully implemented\n1) Creation of a linked list\n" +
                "2) Addition of node (appending)\n3) Creating Stack using ll (push and pop)\n");
    }

    Node head; // Defining head of the linked list...                Attribute!!!

    // Node is nested class so main() will be able to access it!
    public static class Node{
        String data;
        Node next; // this can be considered as method's Attributes/Parameters !

        //Constructor
        Node(String d)
        {   data = d;
            next = null;
        }
    }
/////////////////////////////////////////////////////////////////////////////////

    // below method APPEND's new node at the end of the ll!
    public void append(String new_data){
        // creating new node for given data
        Node new_node = new Node(new_data);
        new_node.next = null; // hence we can easily add this at the end of the ll! :)

        //checking whether the ll is empty or not
        if (head == null){
            head = new_node;
        }
        else{
            Node last = head;
            while(last.next != null){
                last = last.next;
            }
            // after exiting this loop we add our node!
            last.next = new_node;
        }
    }

//////////////////////////////////////////////////////////////////////////////////

    // Pushes new node at the start of the ll! (Stack_Function!)
    public void push(String new_data){
        // FIRST creating a new node for given data !1!!!!
        Node new_node = new Node(new_data);

        new_node.next = head; // connecting the address to current head !!!!!!!!!!

        head = new_node; // redefining new head!!!!!!!!!!!!!!!
    }

    //////////////////////////////////////////////////////////////////////////////////
    // PoP function removes the last node in ll and returns it to the user! (Stack_Function)
    public String pop(){
        Node cursor = head;
// Following we need to check 3 conditions !
//      1.whether list is empty
        if (head == null) {
            return ("Linked List mt...");
        }
//      2.whether head is the only node
        else if(head.next == null){
//            System.out.println(head.data);
            String temp = head.data;
            head = null;
            return (temp);
        }
//      3. else traverse till last node
        else{
            while(cursor.next.next != null){
                cursor = cursor.next;
            }
//            System.out.println(cursor.next.data);
            String temp = cursor.next.data;
            cursor.next = null;
            return (temp);

        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    public void peek(){
        Node pointer = head;
        if (head != null){
            int counter = 0;
            while(pointer.next != null){
//                System.out.println("data: " + pointer.data + " index_ " + counter );
                System.out.println(pointer.data);
                counter++;
                pointer = pointer.next;
            }
//            System.out.println("data: " + pointer.data + " index_ " + counter );
            System.out.println(pointer.data);
        }
        else{
            System.out.println("Linked list is MT :)");
        }
    }
}
