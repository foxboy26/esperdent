<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>

<%@ page import="java.io.File" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.List" %>

<%@ page import="java.io.StringReader" %>
<%@ page import="java.io.Reader" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import= "parser.ASTStart" %>
<%@ page import= "parser.ParseException" %>
<%@ page import= "parser.TritonParser" %>
<%@ page import= "edu.ucsd.cs.triton.codegen.CodeGenerator" %>
<%@ page import= "edu.ucsd.cs.triton.codegen.language.JavaProgram" %>
<%@ page import= "edu.ucsd.cs.triton.operator.BaseLogicPlan" %>
<%@ page import= "edu.ucsd.cs.triton.operator.LogicPlanVisitor" %>
<%@ page import= "edu.ucsd.cs.triton.resources.ResourceManager" %>
<%
String query = request.getParameter("query");
StringReader sr = new java.io.StringReader(query);
Reader r = new BufferedReader(sr);
TritonParser tritonParser;
tritonParser = new TritonParser(r);
ASTStart root = tritonParser.Start();

ResourceManager resourceManager = ResourceManager.getInstance();

LogicPlanVisitor logicPlanVisitor = new LogicPlanVisitor(resourceManager);

root.jjtAccept(logicPlanVisitor, resourceManager);
//System.out.println(resourceManager.getStreamByName("s1"));

out.println("Generating logic plan...");
List<BaseLogicPlan> logicPlanList = logicPlanVisitor.getLogicPlanList();

out.println("Generating trident code...");
String className = "Sample";
CodeGenerator codeGen = new CodeGenerator(logicPlanList, className);

JavaProgram program = codeGen.generate();

out.println("Translating trident code into java code...");
String res = program.translate();

out.println("Generating packge...");
out.println("success!!!!");
out.println(res);
%>
