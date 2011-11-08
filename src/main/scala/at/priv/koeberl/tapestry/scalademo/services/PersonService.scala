package at.priv.koeberl.tapestry.scalademo.services
import scala.collection.immutable.List
import java.util.Date

class Person(
	var firstName: String =  null,
	var lastName : String = null,
	var dateOfBirth : Date = null
)

class PersonService {
  var persons : List[Person] = List()

  def add(p : Person) { persons = p :: persons }

  def delete(p : Person) { persons = persons.remove(_ == p) }

  def searchPerson(search : String) = persons.filter(p => p.firstName.contains(search) || p.lastName.contains(search))

  def listAll() = persons
}