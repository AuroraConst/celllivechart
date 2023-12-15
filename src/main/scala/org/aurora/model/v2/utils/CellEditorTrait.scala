package org.aurora.model.v2.utils
import org.aurora.model.v2.{Grid,GridData}
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

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

  
def cellTextInput(gc:GridData): HtmlElement = 
  val toggleState = Var(StateOne) 
  import org.aurora.model.ui.typeclasses.given
      input(
      backgroundColor <-- toggleState.signal.map(_.color),
      typ := "text",
      onInput.mapToValue --> gc.data,
      onDblClick--> (_ => toggleState.update(_.nextState)),
      onKeyUp --> (e => 
          def htmlInputFocus(c:Coordinate) =
            gc.g.data(c).foreach(_.htmlElement.ref.focus())
          e.keyCode match
          case 40 =>  //down cursor
              htmlInputFocus( gc.coordinate.addY(1) )
          case 38 => //up cursor
              htmlInputFocus( gc.coordinate.addY(-1) )
          case 37 => //left cursor
              htmlInputFocus( gc.coordinate.addX(-1) )
          case 39 => //right cursor
              htmlInputFocus( gc.coordinate.addX(+1) )
          case 9 => 
              dom.window.console.log(s"tabbed ${gc.coordinate}tab tab tab ")

          case _ => ()
          ),
          onFocus --> (e => 
            dom.window.console.log(s"${e.detail} focused ${gc.coordinate}####")
            gc.g.focusedCoodinate.update(_ => s"${gc.coordinate}"  )
            toggleState.update(_ => StateFocused)
        ),
        onBlur --> (e => //focus out
          toggleState.update(_ => StateOne)
        ) ,

      )