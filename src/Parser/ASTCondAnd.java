/* Generated By:JJTree: Do not edit this line. ASTCondAnd.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTCondAnd extends SimpleNode {
  public ASTCondAnd(int id) {
    super(id);
  }

  public ASTCondAnd(EsperdentParser p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(EsperdentParserVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=337cc44fdbc625e8c4deb490fcbdd868 (do not edit this line) */