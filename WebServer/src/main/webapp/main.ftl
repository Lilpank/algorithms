<html>
<head><title>Hello World</title>

<body>
  <form name="insert" action="main" method="post">
    User_id: <input type="text" name="user_id" required/>		<br/>
    Id_chat: <input type="text" name="id_chat" required/>		<br/>
  	Cnt_slave: <input type="text" name="cnt_slave" required/>		<br/>
  	Cnt_master: <input type="text" name="cnt_master" required/>		<br/>
  	Bucks: <input type="text" name="bucks" required/>		<br/>
  	Name: <input type="text" name="name" required/>		<br/>
  	<input type="submit" value="Save"/>
  </form>
  <table class="datatable">
  	<tr>
        <th>id_chat </th>  <th> user_id </th>  <th> cnt_slave </th>  <th> cnt_master </th>  <th> bucks </th>  <th> name </th>
  	</tr>
    <#list users as user>
  	<tr>
        <td>${user.id_chat}</td> <td>${user.user_id}</td> <td>${user.count_slave}</td> <td>${user.count_master}</td> <td>${user.bucks}</td> <td>${user.name}</td>
  	</tr>
    </#list>
  </table>
    <form name="delete" action="main" method="post">
    	User_id: <input type="text" name="user_id" required />	<br/>
    	<input type="submit" value="Delete" />
    </form>
</body>
</html>