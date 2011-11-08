package at.priv.koeberl.tapestry.scalademo.components

import org.apache.tapestry5.ComponentResources
import org.apache.tapestry5.annotations.Parameter
import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.annotations.Import

@Import(stack = Array("bootstrap"))
class Layout {

  @Parameter(defaultPrefix = "message:")
  var pageTitle: String = _

  @Inject
  var componentResources: ComponentResources = _

  def getPageTitle =
    if (pageTitle != null) pageTitle
    else componentResources.getPage().getComponentResources().getMessages().get(componentResources.getPageName() + ".pageTitle")
}