package at.priv.koeberl.tapestry.scalademo.services

import scala.collection.immutable.List
import scala.collection.JavaConverters._

import org.apache.tapestry5.jpa.annotations.CommitAfter

import at.priv.koeberl.tapestry.scalademo.entities.Person
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

trait PersonService {
  @CommitAfter
  def add(p: Person) : Unit;
  @CommitAfter
  def delete(p: Person) : Unit;
  def listAll() : Iterable[Person];
}

class PersonServiceImpl (
  @PersistenceContext
  var em: EntityManager = null) extends PersonService {

  def add(p: Person) = em.persist(p)

  def delete(p: Person) = em.remove(p)

  def listAll() = em.createQuery("FROM Person", classOf[Person]).getResultList().asScala
}