/* Generated By:JJTree: Do not edit this line. ASTUnits.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTUnits extends SimpleNode {
  public ASTUnits(int id) {
    super(id);
  }

  public ASTUnits(EsperdentParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(EsperdentParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=5d73bc5ae103dbaef462597ed660c558 (do not edit this line) */