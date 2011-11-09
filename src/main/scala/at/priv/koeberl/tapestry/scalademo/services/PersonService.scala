package at.priv.koeberl.tapestry.scalademo.services

import scala.collection.immutable.List
import java.util.Date
import javax.persistence.Entity
import javax.persistence.Column
import javax.persistence.PersistenceContext
import javax.persistence.EntityManager
import org.apache.tapestry5.jpa.annotations.CommitAfter
import javax.persistence.GeneratedValue
import javax.persistence.Id
import at.priv.koeberl.tapestry.scalademo.entities.Person

class PersonService(
  @PersistenceContext
  var em: EntityManager = null) {

  @CommitAfter
  def add(p: Person) = em.persist(p)

  @CommitAfter
  def delete(p: Person) = em.remove(p)

  def listAll() = em.createQuery("FROM Person", classOf[Person]).getResultList()
}