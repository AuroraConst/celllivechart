package org.aurora.model



import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

import scala.scalajs.js

package object ui :
  enum EditorToggleState(colorString:String):
    case StateOne extends EditorToggleState("green")
    case StateTwo extends EditorToggleState("blue")
    //Note: you have to surface properties of the enum to the outside
    lazy val color = colorString
    def nextState = this match
      case StateOne => StateTwo
      case StateTwo => StateOne

  import EditorToggleState._  

  //TODO map multiple inputs to a Var[datatype] so that you can change focus depending on key stroke
  
  def cellTextInput(v:Var[String], id:String): HtmlElement = 
    val toggleState = Var(StateOne) 

    div(
      child.text <-- v.signal,
      input(
        backgroundColor <-- toggleState.signal.map(_.color)),
        typ := "text",
        onInput.mapToValue --> v,
        onDblClick--> (_ => toggleState.update(_.nextState)),
        onKeyUp --> (e => if (e.keyCode == 13) toggleState.update(_.nextState)
      )
    )
    