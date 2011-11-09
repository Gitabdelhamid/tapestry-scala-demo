package at.priv.koeberl.tapestry.scalademo.entities

import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Column
import javax.persistence.Entity
import java.util.Date

@Entity
class Person(
  @Column(name = "first_name", length = 40) var firstName: String = null,
  @Column(name = "last_name", length = 40) var lastName: String = null,
  @Column(name = "date_of_birth") var dateOfBirth: Date = null) {
  def this() = this(null, null, null)
  @Id
  @GeneratedValue
  var id: Long = _
}
