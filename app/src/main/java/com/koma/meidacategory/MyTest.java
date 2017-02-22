package com.koma.meidacategory;

/**
 * Created by koma on 2017/2/22.
 */

public class MyTest {
    public MyTest() {
    }

    //1.实现一个函数，在一个有序整型数组中二分查找出指定的值，找到则返回该值的位置，找不到返回-1。
    public static int findIndex(int[] srcArray, boolean desOrder, int value) {
        if (srcArray == null) {
            return -1;
        }
        int length = srcArray.length;
        if (length == 0) {
            return -1;
        }
        if (desOrder) {
            if (value > srcArray[0] || value < srcArray[length - 1]) {
                return -1;
            }
        } else {
            if (value < srcArray[0] || value > srcArray[length - 1]) {
                return -1;
            }
        }
        int start = 0;
        int end = length - 1;
        while (start < end) {
            int middle = start + (end - start) / 2;
            if (srcArray[middle] == value) {
                return middle;
            }
            if (srcArray[middle] < value) {
                if (desOrder) {
                    end = middle - 1;
                } else {
                    start = middle + 1;
                }
            } else {
                if (desOrder) {
                    start = middle + 1;
                } else {
                    end = middle - 1;
                }
            }
        }
        if (srcArray[start] == value) {
            return start;
        } else if (srcArray[end] == value) {
            return end;
        } else {
            return -1;
        }
    }

    // 2. 写一个函数将一个数组转化为一个链表。
// 要求：不要使用库函数，(如 List 等)
    public static class Node {
        Node next;
        int data;
    }

    // 返回链表头
    public Node convert(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        Node head = new Node();
        head.data = array[0];
        int len = array.length;
        Node end = head;
        for (int i = 1; i < len; i++) {
            end = addNode(end, array[i]);
        }
        return head;
    }

    // 给链表尾部添加一个节点
    public Node addNode(Node end, int data) {
        Node node = new Node();
        node.data = data;
        end.next = node;
        return node;
    }

    // 3. 有两个数组，[1,3,4,5,7,9] 和 [2,3,4,5,6,8],用上面的函数生成两个链表 linkA 和
// linkB，再将这两个链表合并成一个链表,结果为[1,2,3,4,5,6,7,8,9].
// 要求：不要生成第三个链表，不要生成新的节点。
// 3.1 使用递归方式实现
//
    public Node comb1(int[] arrayA, int[] arrayB) {
        Node linkA = convert(arrayA);
        Node linkB = convert(arrayB);
        Node head;
        if (linkA.data == linkB.data) {
            head = linkA;
            linkA = linkA.next;
            linkB = linkB.next;
            head.next = null;
        } else if (linkA.data < linkB.data) {
            head = linkA;
            linkA = linkA.next;
            head.next = null;
        } else {
            head = linkB;
            linkB = linkB.next;
            head.next = null;
        }
        Node end = head;
        comb(end, linkA, linkB);
        return head;
    }

    // 实现递归
    public void comb(Node end, Node headA, Node headB) {
        if (headA == null && headB == null) {
            return;
        } else if (headA == null) {
            end.next = headB;
            return;
        } else if (headB == null) {
            end.next = headA;
            return;
        }
        if (headA.data < headB.data) {
            end.next = headA;
            headA = headA.next;
            end = end.next;
            end.next = null;
            comb(end, headA, headB);
        } else if (headA.data == headB.data) {
            end.next = headA;
            headA = headA.next;
            headB = headB.next;
            end = end.next;
            end.next = null;
            comb(end, headA, headB);
        } else {
            end.next = headB;
            headB = headB.next;
            end = end.next;
            end.next = null;
            comb(end, headA, headB);
        }
    }

    // 3.2 使用循环方式实现
// 循环实现
    public Node comb(int[] arrayA, int[] arrayB) {
// 转换链表
        Node linkA = convert(arrayA);
        Node linkB = convert(arrayB);
// 获取头节点
        Node head;
        if (linkA.data == linkB.data) {
            head = linkA;
            linkA = linkA.next;
            linkB = linkB.next;
            head.next = null;
        } else if (linkA.data < linkB.data) {
            head = linkA;
            linkA = linkA.next;
            head.next = null;
        } else {
            head = linkB;
            linkB = linkB.next;
            head.next = null;
        }
        Node end = head;
// 依次将较小的节点加到链表尾部
        while (linkA != null && linkB != null) {
            if (linkA.data < linkB.data) {
                end.next = linkA;
                linkA = linkA.next;
                end = end.next;
                end.next = null;
            } else if (linkA.data == linkB.data) {
                end.next = linkA;
                linkA = linkA.next;
                linkB = linkB.next;
                end = end.next;
                end.next = null;
            } else {
                end.next = linkB;
                linkB = linkB.next;
                end = end.next;
                end.next = null;
            }
        }
// 如果其中一个链表为空，将另外一个链表直接添加到合成链表尾部
        if (linkA == null) {
            end.next = linkB;
        } else if (linkB == null) {
            end.next = linkA;
        }
        return head;
    }
}
