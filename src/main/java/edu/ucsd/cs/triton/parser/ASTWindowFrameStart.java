/* Generated By:JJTree: Do not edit this line. ASTWindowFrameStart.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTWindowFrameStart extends SimpleNode {
  public ASTWindowFrameStart(int id) {
    super(id);
  }

  public ASTWindowFrameStart(EsperdentParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(EsperdentParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=0e5ef091e519b2ba7c5a5c6582a5f97f (do not edit this line) */