/* Generated By:JJTree: Do not edit this line. ASTCondGt.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTCondGt extends SimpleNode {
  public ASTCondGt(int id) {
    super(id);
  }

  public ASTCondGt(EsperdentParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(EsperdentParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=efaca7b07c31c64c6fb59b73b9fada8c (do not edit this line) */
