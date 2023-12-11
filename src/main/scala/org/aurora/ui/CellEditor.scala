package org.aurora.model



import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

import scala.scalajs.js
import typings.std.stdStrings.focusin
import scala.scalajs.js.`new`
import typings.std.stdStrings.focusout

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
      input(
        backgroundColor <-- toggleState.signal.map(_.color),
        typ := "text",
        onInput.mapToValue --> gc.value,
        onDblClick--> (_ => toggleState.update(_.nextState)),
        onKeyUp --> (e => 
          e.keyCode match
            case 40 =>  //down cursor
              val newCoord = gc.coordinate().addY(1)
              gc.grid.get(newCoord.x,newCoord.y).inputElement.ref.focus()
             
            case 38 => //up cursor
              val newCoord = gc.coordinate().addY(-1)
              gc.grid.get(newCoord.x,newCoord.y).inputElement.ref.focus()
            case 37 => //left cursor
              val newCoord = gc.coordinate().addX(-1)
              gc.grid.get(newCoord.x,newCoord.y).inputElement.ref.focus() 
            case 39 => //right cursor
              val newCoord = gc.coordinate().addX(1)
              gc.grid.get(newCoord.x,newCoord.y).inputElement.ref.focus()   
            case 9 => 
              dom.window.console.log(s"tabbed ${gc.id}tab tab tab ")

            case _ => ()
          ),
        onFocus --> (e => 
          dom.window.console.log(s"${e.detail} focused ${gc.id}####")
          
          gc.grid.focusedCoordinate.update(_ => gc.id) 
          toggleState.update(_ => StateFocused)
        ),
        onBlur --> (e => //focus out
          toggleState.update(_ => StateOne)
        ),

      )