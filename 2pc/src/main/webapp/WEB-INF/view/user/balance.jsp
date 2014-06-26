<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>2pc</title>
</head>
<body>
	<a href="balance.do">refresh to check user balance</a>
	
	<center>
	<!-- 表单1 -->
		<form:form path="/user/transfer.do" method="post" commandName="userAccount">
		<form:hidden path="accountId"/>
			<table>
				<tr>
					<td>用户：${user1.username } </td>
					<td>账户当前余额： ${user1.balance } </td>
					<td>输入金额：<form:input	path="transferAmount" /> >>转账给${user2.username }
					</td>
					<td><input type="submit"  /></td>
				</tr>
			</table>
		</form:form>
	<!-- 表单2 -->
		<form:form path="/user/transfer.do" method="post"  commandName="userAccount">
		<form:hidden path="accountId"/>
			<table>
				<tr>
					<td>用户：${user2.username } </td>
					<td>账户当前余额： ${user2.balance } </td>
					<td>输入金额：<form:input	path="transferAmount" /> >>转账给${user1.username }
					</td>
					<td><input type="submit"  /></td>
				</tr>
			</table>
		</form:form>
	</center>

</body>
</html>