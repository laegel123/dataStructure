package com.example.demo.Queue.Interface;

public interface Queue<E> {

    /**
     * 큐의 가장 마지막에 요소를 추가한다.
     * @param e : 큐에 추가할 요소
     * @return 큐에 요소가 정상적으로 추가되었을 경우 true 를 반환한다.
     */
    boolean offer(E e);

    /**
     * 큐의 첫 번째 요소를 삭제하고 삭제 된 요소를 반환한다.
     * @return 큐의 삭제 된 요소를 반환한다.
     */
    E poll();

    /**
     * 큐의 첫 번째 요소를 반환한다.
     * @return 큐의 첫 번째 요소를 반환한다.
     */
    E peek();
}
