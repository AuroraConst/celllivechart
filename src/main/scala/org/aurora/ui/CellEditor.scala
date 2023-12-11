package org.aurora.model



import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

import scala.scalajs.js
import typings.std.stdStrings.focusin

package object ui :
  enum EditorToggleState(colorString:String):
    case StateOne extends EditorToggleState("green")
    case StateTwo extends EditorToggleState("blue")
    case StateFocused extends EditorToggleState("red")
    //Note: you have to surface properties of the enum to the outside
    lazy val color = colorString
    def nextState = this match
      case StateOne => StateTwo
      case StateTwo => StateOne
      case StateFocused => StateFocused
      

  import EditorToggleState._  

  
  def cellTextInput(gc:GridCell): HtmlElement = 
    val toggleState = Var(StateOne) 
    import org.aurora.model.ui.typeclasses.given
    div(
      child.text <-- gc.value.signal,
      input(
        backgroundColor <-- toggleState.signal.map(_.color)),
        typ := "text",
        onInput.mapToValue --> gc.value,
        onDblClick--> (_ => toggleState.update(_.nextState)),
        onKeyUp --> (e => 
          // dom.window.console.log(s"focused ${gc.id}!!!!!!!!!!!!!!!!!")
          e.keyCode match
            case 13 => toggleState.update(_.nextState)
            case 9 => 
              dom.window.console.log(s"tabbed ${gc.id}tab tab tab ")

            case _ => ()

          
          ),
        onClick --> (_ => 
          dom.window.console.log(s"clicked ${gc.id}xxxxxxxxxxxxxx")
          gc.grid.focusedCoordinate.update(_ => gc.id)),  
        onFocus --> (e => 
          //TODO ERROR: this does not seem to capture any events??
          //I have to use the keyUp to capture the tab event instead
          dom.window.console.log(s"$e focused ${gc.id}####")
          gc.grid.focusedCoordinate.update(_ => gc.id) 
        ),
    )