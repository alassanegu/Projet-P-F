
import munit.FunSuite

class TesteQueue extends FunSuite {
  test("mettre en file d'attente et retirer de la file d'attente devrait fonctionner correctement") {
    val A = Queue[Int](Nil, Nil) // in = Nil, out = Nil
    println("A = " + A)
    val B = A.enqueue(1) // = Queue(1->Nil, Nil)
    println("B = " + B)
    val C = B.enqueue(2) // = Queue(2->1->Nil, Nil)
    println("C = " + C)
    val D = C.enqueue(3) // = Queue(3->2->1->Nil, Nil)
    println("D = " + D)
    val E = D.dequeue() // = (1, Queue(Nil, 2->3->Nil) ici 'in' est inversée et placée en `out`
    println("E = " + E)
    val F = E._2.enqueue(4) // = Queue(4->Nil, 2->3->Nil)
    println("F = " + F)
    val G = F.enqueue(5) // = Queue(5->4->Nil, 2->3->Nil)
    println("G = " + G)
    val H = G.dequeue() // = (2, Queue(5->4->Nil, 3->Nil)
    println("H = " + H)
    val I = H._2.dequeue() // = (3, Queue(5->4->Nil, Nil))
    println("I = " + I)
    val J = I._2.dequeue() // = (4, Queue(Nil, 5->Nil))
    println("J = " + J)
    val K = J._2.dequeue() // = (5, Queue(Nil, Nil))
    println("K = " + K)
    val q1 = Queue[Int]().enqueue(1).enqueue(2).enqueue(3)
    assertEquals(q1.dequeue(), (1, Queue[Int](List(2, 3), Nil)))
    assertEquals(q1.dequeue()._2.dequeue(), (2, Queue[Int](List(3), Nil)))
    assertEquals(q1.dequeue()._2.dequeue()._2.dequeue(), (3, Queue[Int](Nil, Nil)))
  }

  test("headOption et rearOption doivent renvoyer None lorsqu'ils sont vides") {
    val q1 = Queue[Int]()
    assertEquals(q1.headOption(), None)
    assertEquals(q1.rearOption(), None)
  }

  test("headOption et rearOption doivent renvoyer les éléments corrects lorsqu'ils ne sont pas vides") {
    val q1 = Queue[Int]().enqueue(1).enqueue(2).enqueue(3)
    assertEquals(q1.headOption(), Some(1))
    assertEquals(q1.rearOption(), Some(3))
  }

  test("length doit renvoyer la longueur correcte lorsqu'il n'est pas vide") {
    val q1 = Queue[Int]().enqueue(1).enqueue(2).enqueue(3)
    assertEquals(q1.length(), 3)
  }

  test("toList doit renvoyer une liste vide lorsqu'elle est vide") {
    val q1 = Queue[Int]()
    assertEquals(q1.toList(), Nil)
  }

  test("toList doit renvoyer une liste avec les éléments corrects lorsqu'elle n'est pas vide") {
    val q1 = Queue[Int]().enqueue(1).enqueue(2).enqueue(3)
    assertEquals(q1.toList(), List(1, 2, 3))
  }

  test("la carte doit renvoyer une nouvelle file d'attente avec des éléments transformés") {
    val q1 = Queue[Int]().enqueue(1).enqueue(2).enqueue(3)
    assertEquals(q1.map(_ * 2), Queue[Int](List(2, 4, 6), Nil))
  }

  test("foldLeft devrait renvoyer le résultat correct") {
    val q1 = Queue[Int]().enqueue(1).enqueue(2).enqueue(3)
    assertEquals(q1.foldLeft(0)((a, _) => a + 1), 12)
  }

  test("dequeueTotal doit renvoyer (None, Queue()) pour une file d'attente vide") {
    val q1 = Queue[Int]()
    assertEquals(q1.dequeueTotal(), (None, Queue[Int]()))
  }

  test("dequeueTotal doit renvoyer le résultat correct pour la file d'attente non vide") {
    val q1 = Queue[Int]().enqueue(1).enqueue(2).enqueue(3)
    assertEquals(q1.dequeueTotal(), (Some(1), Queue[Int](List(2, 3), Nil)))
  }

  test("isEmptyWithMatch doit renvoyer true lorsqu'il est vide") {
    val q1 = Queue[Int]()
    assertEquals(q1.isEmptyWithMatch, true)
  }

  test("isEmptyWithMatch doit renvoyer false lorsqu'il n'est pas vide") {
    val q1 = Queue[Int]().enqueue(1)
    assertEquals(q1.isEmptyWithMatch, false)
  }
}
