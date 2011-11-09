package at.priv.koeberl.tapestry.scalademo.services

import java.util.Collections
import org.apache.tapestry5.ioc.annotations.Symbol
import org.apache.tapestry5.services.javascript.JavaScriptStack
import org.apache.tapestry5.services.javascript.StylesheetLink
import org.apache.tapestry5.services.AssetSource
import org.apache.tapestry5.SymbolConstants._
import java.util.Arrays
import org.apache.tapestry5.ioc._
import org.apache.tapestry5.ioc.services._
import org.apache.tapestry5.ioc.annotations.Contribute
import org.apache.tapestry5.services.messages.ComponentMessagesSource
import org.apache.tapestry5.ioc.annotations.Value
import org.apache.tapestry5.grid.GridDataSource
import scala.collection.immutable.List
import org.apache.tapestry5.internal.grid.CollectionGridDataSource
import scala.collection.JavaConverters._
import org.apache.tapestry5.SymbolConstants
import org.apache.tapestry5.jpa.JpaTransactionAdvisor
import org.apache.tapestry5.ioc.annotations.Match

object AppModule {
  
  def bind(binder : ServiceBinder) {
    binder.bind(classOf[PersonService])
  }
  
  def contributeApplicationDefaults(configuration : MappedConfiguration[String, Object]) {
    configuration.add(SymbolConstants.DEFAULT_STYLESHEET, "classpath:/at/priv/koeberl/tapestry/scalademo/style/tapestry.css")
    configuration.add(SymbolConstants.PRODUCTION_MODE, Boolean.box(false))
  }

  def contributeJavaScriptStackSource(configuration: MappedConfiguration[String, JavaScriptStack]) {
    configuration.addInstance("bootstrap", classOf[BootstrapStack])
  }

  @Contribute(classOf[ComponentMessagesSource])
  def addApplicationMessages(configuration: OrderedConfiguration[Resource],
    @Value("classpath:/at/priv/koeberl/tapestry/scalademo/app.properties") applicationProperties: Resource) {
    configuration.add("tapestry-scala", applicationProperties)
  }

  @Contribute(classOf[TypeCoercer])
  def addListCoercion(configuration : Configuration[CoercionTuple[Seq[AnyRef], GridDataSource]]) {
	  configuration.add(new CoercionTuple(classOf[Seq[AnyRef]], 
	      classOf[GridDataSource], new ScalaCollectionGridDataSourceCoercion))
  }

  // TODO check why advising PersonService does not work
  @Match(Array("PersonService"))
  def adviseTransactionally(advisor: JpaTransactionAdvisor, receiver: MethodAdviceReceiver) {
    advisor.addTransactionCommitAdvice(receiver);
  }
}

class BootstrapStack(assetSource: AssetSource, @Symbol(PRODUCTION_MODE) productionMode: Boolean) extends JavaScriptStack {
  def getStacks = Collections.emptyList()
  def getInitialization = null
  def getJavaScriptLibraries = Collections.emptyList()
  def getStylesheets = {
    val css = if (productionMode) "classpath:/at/priv/koeberl/tapestry/scalademo/style/bootstrap.min.css"
    		  else "classpath:/at/priv/koeberl/tapestry/scalademo/style/bootstrap.css"
    Arrays.asList(new StylesheetLink(assetSource.getUnlocalizedAsset(css)))
  }
}

class ScalaCollectionGridDataSourceCoercion extends Coercion[Seq[AnyRef], GridDataSource] {
  def coerce(list : Seq[AnyRef]) = new CollectionGridDataSource(list.asJava)
}
