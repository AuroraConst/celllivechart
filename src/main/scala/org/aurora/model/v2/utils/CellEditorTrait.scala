package org.aurora.model.v2.utils
import org.aurora.model.v2.{Grid,GridData}
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom

enum EditorToggleState(colorString:String):
  case UnSelected extends EditorToggleState("green")
  case StateTwo extends EditorToggleState("blue")
  case StateFocused extends EditorToggleState("red")
  case RowSelected extends EditorToggleState("#9F5EB2")
  //Note: you have to surface properties of the enum to the outside
  lazy val color = colorString
  def nextState = this match
    case UnSelected => StateTwo
    case StateTwo => UnSelected
    case StateFocused => StateFocused
    case _ => UnSelected
    

import EditorToggleState._  

  
def cellTextInput(gd:GridData): HtmlElement = 
  input(
  backgroundColor <-- gd.toggleState.signal.map(_.color),
  typ := "text",
  onInput.mapToValue --> gd.cellText,
  onDblClick--> (_ => gd.toggleState.update(_.nextState)),
  onKeyDown --> (e => 
    def htmlInputFocus(c:Coordinate) =
      gd.g.data(c).foreach{elem =>    
        elem.inputHtmlElement.ref.focus()
      }
    e.keyCode match
    case 40 =>  //down cursor
      // dom.window.console.log(s"down ${gd.coordinate}down*down*down* ")
      htmlInputFocus( gd.coordinate.addY(1) )
    case 38 => //up cursor
      htmlInputFocus( gd.coordinate.addY(-1) )
    case 37 => //left cursor
      htmlInputFocus( gd.coordinate.addX(-1) )
    case 39 => //right cursor
      htmlInputFocus( gd.coordinate.addX(+1) )
    case 9 => //tab
      dom.window.console.log(s"tabbed ${gd.coordinate}tab tab tab ")

    case _ => ()
    ),
    onFocus --> (e => 
      gd.g.focusedCoodinate.update(_ => Some(gd.coordinate) )
      selectedRow(gd).foreach(d => d.toggleState.update(_ => RowSelected))
      gd.toggleState.update(_ => StateFocused)
    ),
    onBlur --> (e => //focus out
      gd.toggleState.update(_ => UnSelected)
      selectedRow(gd).foreach(d => d.toggleState.update(_ => UnSelected))
    ) ,
  )


def selectedRow(gd:GridData) =
    gd.g.xRange.toList
      .filter(_ != gd.x)
      .map{x =>gd.g.data(x,gd.y) }
      .filter(d => d.isDefined)
      .map(_.get)
