package old;


import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

public class RequestQueue {
    private ArrayDeque<Request> queue;

    RequestQueue() {
        queue = new ArrayDeque<>();
    }

    RequestQueue(Vector<Request> requests) {
        queue = new ArrayDeque<>();
        queue.addAll(requests);
    }

    public Iterator iterator() {
        return queue.iterator();
    }

    public Request pollFirst() {
        return queue.pollFirst();
    }

    public Request pollLast() {
        return queue.pollLast();
    }

    public long getFrontTime() {
        return queue.peekFirst().getTime();
    }

    public void offerFirst(Request req) {
        queue.offerFirst(req);
    }

    public void offerLast(Request req) {
        queue.offerLast(req);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public void sort() {
        int n = queue.size();
        if (n <= 1) {
            return;
        }
        Request[] requests = new Request[n];
        for (int i = 0; i < n; ++ i) {
            requests[i] = queue.pollFirst();
        }
        Arrays.sort(requests);
        for (int i = 0; i < n; ++ i) {
            queue.offerLast(requests[i]);
        }
    }
}
