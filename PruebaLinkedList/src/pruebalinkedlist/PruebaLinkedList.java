//Prueba git
package pruebalinkedlist;
/*
    @Sergio Carrillo Osorio
    @17/08/2026
*/
class Student {
    String name;
    int score;
    Student next;
    public Student(String name, int score){
        this.name = name;
        this.score = score;
        this.next = null;
    }
}
class LnkdLst{
    Student head;
    Student tail;
    int index = 0;
    public LnkdLst()//Constructor
    {
        this.head = null;
        this.tail = null;
    }
    //Agrega un nuevo estudiante al final de la lista
    public void add(String name, int score){
    Student newStudent = new Student(name,score);
    if (head == null){
        head = newStudent;
        tail = newStudent;
        this.index++;
        return;
    }
    tail.next = newStudent;
    tail = newStudent;
    this.index++;
  }
  public void print() {
      Student current = this.head;
      while (current !=null){
          System.out.println(current.name + " - " + current.score);
          current = current.next;
      }
  }
  public Student retrieve (String searchedName){
      Student current = this.head;
      while (current!=null){
          if (current.name.equals(searchedName))
          {
              return current;
          }
          current = current.next;
      }
      return null;
  }
  public void remove (int n){
      if (head == null){
          return; //Empty List
      }
      if (n<0){//Negative index
          return;
      }
      if (n>= this.index){
          return; //Index out of bounds
      }
      if (n == 0){
          head = head.next;
          this.index--;
          return;
      }
      if (n == this.index - 1){
          Student current = head;
          while (current.next != tail){
              current = current.next;
          }
          current.next = null;
          tail = current;
          this.index--;
          return;
      }
      int index = 0;
      Student current = head;
      while (current!= null && index <(n-1)){
          current = current.next;
          index++;
      }
      if (current==null || current.next == null)
      {
          return; //position out of range
      }
      current.next = current.next.next;
      
      this.index--;
  }
}
public class PruebaLinkedList {

    public static void main(String[] args) {
        LnkdLst lista = new LnkdLst();
        System.out.println("Attempting to remove from empty list");
        lista.remove(0);
        lista.print();
        System.out.println("-----");
        lista.add("Linus", 90);
        lista.add("Ada", 95);
        lista.add("Paul", 78);
        lista.add("Grace", 100);
        lista.print();
        Student s = lista.retrieve("Ada");
        if (s != null) {
            System.out.println("Found: " + s.name + " - " + s.score);
        } else {
            System.out.println("Student not found");
        }
        lista.remove(0);
        System.out.println("Attempting to remove the first student:");
        lista.print();
        lista.remove(1);
        System.out.println("Attempting to remove the first student:");
        lista.print();
        System.out.println("Attempting to remove an object in the middle");
        lista.remove(2);
        lista.print();
        System.out.println("Attempting to remove an object at the end");
        lista.remove(3);
        lista.print();
        System.out.println("Attempting to remove an object with a negative index");
        lista.remove(-1);
        lista.print();
            
    }
    
}
