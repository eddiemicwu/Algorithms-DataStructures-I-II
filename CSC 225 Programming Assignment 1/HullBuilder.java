/* HullBuilder.java
   CSC 225 - Summer 2019

   Starter code for Convex Hull Builder. Do not change the signatures
   of any of the methods below (you may add other methods as needed).

   B. Bird - 03/18/2019
   (Wenbo Wu /V00928620 /June15,2019)
*/
import java.util.LinkedList;

public class HullBuilder{
    private LinkedList<Point2d> convexHull =new LinkedList<Point2d>();
    private LinkedList<Point2d> inner = new LinkedList<Point2d>();
    private LinkedList<Point2d> undefined = new LinkedList<Point2d>();
    private Point2d initial = null;
    private int count=0;
    /* Add constructors as needed */

    /* addPoint(P)
       Add the point P to the internal point set for this object.
       Note that there is no facility to delete points (other than
       destroying the HullBuilder and creating a new one). 
    */
    public void addPoint(Point2d P){
        /*
        add point to undefined linkedlist.
        find the most left point(p0) which is the starting point.
        */
        if(undefined.isEmpty()||initial.x>P.x){
            initial=P;
        }
        undefined.addFirst(P);
    }
    /*
    calculate a point with inital point to get the degree angle.
    */
    public float angle(Point2d a){
        if(a==initial){
            return 0;
        }
        return 360-(float) Math.toDegrees(Math.atan2(initial.x-a.x, initial.y-a.y));
    }
    
    public void mergeSort(LinkedList<Point2d> a){
        LinkedList<Point2d> b = new LinkedList<Point2d>();
        Point2d t, c, d;
        double temp, current;
        int n1, n2;
        n1= (int) Math.floor(a.size()/2);
        n2=a.size()-n1;
        while(n1!=0){
            b.addLast(a.removeFirst());
            n1--;
        }
        if(n2>2){
            mergeSort(a);
            a=undefined;
        }else{
            c=a.getFirst();
            d=a.getLast();
            temp=angle(c);
            if(temp<0){
                temp=temp*-1;
            }
            current=angle(d);
            if(current<0){
                current=current*-1;
            }
            if(temp>current){
                t=a.removeFirst();
                a.addLast(t);
            }else if(temp==current){
                if(c.x>d.x){
                    t=a.removeFirst();
                    a.addLast(t);
                }
            }
        }
        if(b.size()>2){
            mergeSort(b);
            b=undefined;
        }else{
            c=b.getFirst();
            d=b.getLast();
            temp=angle(c);
            if(temp<0){
                temp=temp*-1;
            }
            current=angle(d);
            if(current<0){
                current=current*-1;
            }
            if(temp>current){
                t=b.removeFirst();
                b.addLast(t);
            }else if(temp==current){
                if(c.x>d.x){
                    t=b.removeFirst();
                    b.addLast(t);
                }
            }
        }
        int pointer=0;
        int s =0;
        for(int i =0; i<n2;i++){
            c=a.getFirst();
            temp=angle(c);
            if(temp<0)
                temp=temp*-1;
            d=b.getLast();
            current=angle(d);
            if(current<0)
                current=current*-1;
            if(temp>current){
                while(!a.isEmpty())
                    b.addLast(a.removeFirst());
                i=n2;
            }else if(temp<current){
                d=b.get(pointer);
                current=angle(d);
                if(current<0)
                    current=current*-1;
                if(current>temp){
                    b.add(pointer,a.removeFirst());
                    pointer++;
                }else if(temp==current){
                    if(c.x<d.x){
                        b.add(pointer, a.removeFirst());
                        pointer++;
                    }else if (c.x>d.x){
                        s=1;
                        s=s+pointer;
                        d=b.get(s);
                        current=angle(d);
                        if(current<0)
                            current=current*-1;
                        while(temp==current){
                            if(c.x>d.x){
                                s++;
                                d=b.get(s);
                                current=angle(d);
                                if(current<0)
                                    current=current*-1;
                            }
                        }
                        b.add(s,a.removeFirst());
                    }
                }else{
                    s++;
                    pointer++;
                    d=b.get(s);
                    current=angle(d);
                    if(current<0)
                        current=current*-1;
                    while(temp>=current){
                        if(temp==current && c.x<d.x){
                            current=temp+1;
                        }else{
                            pointer++;
                            if(pointer==b.size()){
                                pointer--;
                            }
                            d=b.get(pointer);
                            current=angle(d);
                            if(current<0)
                                current=current*-1;
                        }
                    }
                    b.add(pointer,a.removeFirst());
                }
            }else{
                if(c.x>d.x){
                    b.addLast(a.removeFirst());
                }else{
                    int k = b.size()-1;
                    k--;
                    d=b.get(k);
                    current=angle(d);
                    if(current<0)
                        current=current*-1;
                    while(temp==current){
                        if(c.x<d.x){
                            k--;
                            d=b.get(k);
                            current=angle(d);
                            if(current<0)
                                current=current*-1;
                        }else{
                            current=1;
                        }
                    }
                    k++;
                    b.add(k, a.removeFirst());
                }
            }
        }
        undefined=b;
    }
    /* getHull()
       Return a java.util.LinkedList object containing the points
       in the convex hull, in order (such that iterating over the list
       will produce the same ordering of vertices as walking around the 
       polygon).
    */
    public LinkedList<Point2d> getHull(){
        Point2d P, Q, R;
        int count =0;
        mergeSort(undefined);
        convexHull.addFirst(initial);//store p0 into the convexHull
        undefined.addLast(initial);
        for(int s=1;s<undefined.size()*2;s++ ){
            P=undefined.get(count++);
            Q=undefined.get(count++);
            if(Q==initial){
                convexHull.addLast(Q);
                return convexHull;
            }
            R=undefined.get(count);
            if(R.chirality(P,Q,R)==-1||R.chirality(P,Q,R)==0){
                convexHull.addLast(Q);
                count--;
            }else{
                count--;
                inner.addLast(undefined.remove(count--));
            }
        }
        return convexHull;
    }

    /* isInsideHull(P)
       Given an point P, return true if P lies inside the convex hull
       of the current point set (note that P may not be part of the
       current point set). Return false otherwise.
     */
    public boolean isInsideHull(Point2d P){
        int watch=0;
        int check=0;
        Point2d Q,R;
        for(int i =0; i<convexHull.size();i++){
            Q=convexHull.get(check++);
            if(check==convexHull.size()){
                check=0;
            }
            R=convexHull.get(check);
            if(P.chirality(P,Q,R)==-1||P.chirality(Q,R,P)==0){
                watch++;
            }
        }
        if(watch==convexHull.size()){
            return true;
        }
        return false;
    }
}