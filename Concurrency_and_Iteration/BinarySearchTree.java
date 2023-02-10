import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;


/**
 * Binary Search Tree
 *
 * @param <T>
 * @author Yan Kun (Alex) Meng
 */
public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {
    private Node<T> root;
    private int size;
    private final String name;

    public BinarySearchTree(String name) {
        this.name = name;
        this.size = 0;
    }

    public void addAll(Collection<? extends T> c) {
        for (T data : c) {
            add(data);
        }
    }

    public void add(T data) {
        root = add(data, root);
    }

    private Node<T> add(T data, Node<T> node) {
        if (node != null) {
            if (data == null) {
                node.right = add(data, node.right);
            } else {
                if (node.getData() == null) {
                    node = new Node<>(data);
                    node.right = add(null, node.right);
                }
                if (data.compareTo(node.data) < 0) {
                    node.left = add(data, node.left);
                }
                if (data.compareTo(node.data) > 0) {
                    node.right = add(data, node.right);
                }
            }
        } else {
            node = new Node<>(data);
            size++;
        }
        return node;
    }

    public static <T extends Comparable<T>> List<T> merge(BinarySearchTree<T> t1, BinarySearchTree<T> t2){
        final Merger<T> pc = new Merger<>(t1,t2);
        // Left Side
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    pc.pushLeft();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        // Right side
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    pc.pushRight();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return pc.result;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyIterator<T>(root);
    }

    @Override
    public String toString() {
        return "[" + this.name + "] " + root;
    }

    private static class Node<K extends Comparable<K>> {
        private K data;
        private Node<K> left, right;

        public Node(K data) {
            this.data = data;
        }

        public K getData() {
            return data;
        }

        public Node<K> getLeft() {
            return left;
        }

        public Node<K> getRight() {
            return right;
        }

        public void setData(K data) {
            this.data = data;
        }

        public void setLeft(Node<K> left) {
            this.left = left;
        }

        public void setRight(Node<K> right) {
            this.right = right;
        }

        @Override
        public String toString() {
            String str = "";
            if(data != null){
                str += data;
            }
            if(this.getLeft() != null){
                str += " L:(" + getLeft() + ")";
            }
            if(this.getRight() != null){
                str += " R:(" + getRight() + ")";
            }
            return str;
        }
    }
    private static class MyIterator<K extends Comparable<K>> implements Iterator<K>{
        private final Stack<Node<K>> stack;
        public MyIterator(Node<K> root){
            this.stack = new Stack<>();
            this.inorder(root);
        }
        private void inorder(Node<K> root){
            while(root != null){
                this.stack.push(root);
                root = root.left;
            }
        }
        @Override
        public K next() {
            Node<K> topmost = this.stack.pop();
            if(topmost.getRight() != null){
                this.inorder(topmost.right);
            }
            return topmost.getData();
        }
        @Override
        public boolean hasNext() {
            return this.stack.size() > 0;
        }
    }
    private static class Merger<K extends Comparable<K>> {
        // list is shared by both method 1 and method 2
        List<K> result = new ArrayList<>();
        List<K> left;
        List<K> right;

        boolean leftIsSmaller;
        int indexLeft = 0;
        int indexRight = 0;

        private void updateBool(int indexLeft, int indexRight){
            if(indexLeft >= left.size()){
                leftIsSmaller = false;
            } else if(indexRight >= right.size()){
                leftIsSmaller = true;
            } else leftIsSmaller = left.get(indexLeft).compareTo(right.get(indexRight)) < 0;
        }

        public Merger(BinarySearchTree<K> left, BinarySearchTree<K> right){
            this.left = new ArrayList<>();
            for(K elem: left){
                this.left.add(elem);
            }
            this.right = new ArrayList<>();
            for(K elem: right){
                this.right.add(elem);
            }
            updateBool(indexLeft, indexRight);
        }

        public void pushLeft() throws InterruptedException
        {
            int index = indexLeft;
            while(index < left.size()){
                synchronized(this){
                    while(!leftIsSmaller){
                        try{wait();}catch(InterruptedException ignore){}
                    }
                    // leftIsSmaller
                    result.add(left.get(index));
                    //check
                    index++;
                    indexLeft = index;
                    updateBool(indexLeft, indexRight);
                    notify();
                }
            }
        }

        public void pushRight() throws InterruptedException
        {
            int index = indexRight;
            while(index < right.size()){
                synchronized(this){
                    while(leftIsSmaller){
                        try{wait();}catch(InterruptedException ignore){}
                    }
                    // right is smaller
                    result.add(right.get(index));
                    //check
                    index++;
                    indexRight = index;
                    updateBool(indexLeft, indexRight);
                    notify();
                }
            }
        }

    }
}
