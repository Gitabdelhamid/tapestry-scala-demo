package at.priv.koeberl.tapestry.scalademo.pages

import at.priv.koeberl.tapestry.scalademo.services.PersonService
import org.apache.tapestry5.ioc.annotations.Inject
import at.priv.koeberl.tapestry.scalademo.services.Person

class PersonEditor {

  @Inject
  var personService : PersonService = _

  var currentPerson : Person = _

  var person : Person = _

  def getPersonList = personService.listAll()

  def onPrepare() {
    person = new Person()
  }

  def onSuccess() {
    personService.add(person)
  }

}