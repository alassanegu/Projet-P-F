case class Queue[T](in:List[T] = Nil, out:List[T] = Nil) {

  /** Ajoute un élément `x` à la tête de la file d'attente. */
  def enqueue(x:T):Queue[T] = Queue(x :: in, out)

  /** Supprime le dernier élément de la file d'attente et le renvoie sous forme de tuple avec la file d'attente mise à jour.
   * Si la file d'attente est vide, une NoSuchElementException est levée.
   */
  def dequeue():(T,Queue[T]) = out match {
    case hd :: tl => (hd, Queue(in, tl))
    case Nil => in.reverse match {
      case hd :: tl => (hd, Queue(Nil, tl))
      case Nil => throw new NoSuchElementException
    }
  }

  /** Renvoie le premier élément de la file d'attente, s'il existe (dernier élément saisi).
   * Renvoie None si la file d'attente est vide.
   */
  def headOption():Option[T] = out.headOption.orElse(in.reverse.headOption)

  /** Renvoie vrai si la file d'attente est vide, faux sinon. */
  def isEmpty:Boolean = in.isEmpty && out.isEmpty

  /** Renvoie la taille de la file d'attente. */
  def length():Int = in.length + out.length

  /** Renvoie le dernier élément de la file (celui inséré en premier) sans le modifier.
   * Renvoie None si la file d'attente est vide.
   */
  def rearOption():Option[T] = in.headOption.orElse(out.reverse.headOption)

  /** Convertit la file d'attente en une liste à liens simples. */
  def toList(): List[Any] = in.reverse ::: out

  /** Applique une fonction `f` à chaque élément de la file d'attente et renvoie une nouvelle file d'attente avec les éléments transformés. */
  def map[B](f: T => B): Queue[B] = Queue(in.map(f), out.map(f))

  /** Applique un pli à gauche aux éléments de la file d'attente, en commençant par une valeur d'accumulateur initiale `z`. */
  def foldLeft[B](z:B)(f:(B,Any) => B):B = (in.reverse ::: out).foldLeft(z)(f)

  /** Supprime le dernier élément de la file d'attente et le renvoie sous forme de tuple avec la file d'attente mise à jour.
   * Si la file d'attente est vide, renvoie (None, Queue()).
   */
  def dequeueTotal(): (Option[T], Queue[T]) =
    if (isEmpty) (None, Queue())
    else (Some(dequeue()._1), dequeue()._2)

  /** Renvoie vrai si la file d'attente est vide, faux sinon. */
  def isEmptyWithMatch: Boolean = this match {
    case Queue(Nil, Nil) => true
    case _ => false
  }
}