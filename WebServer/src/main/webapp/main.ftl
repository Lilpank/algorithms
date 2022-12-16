<html>
<head>
<link rel="stylesheet" href="styles.css">

<body>
  <div class="wrapper">
      <div class="find1">
              <h1>Find in the joined-table the rows of first table</h1>
              <form name="find" action="find" method="post">
                User_id: <input type="text" name="user_id" required />	<br/>
                <input type="submit" value="find" />
              </form>
      </div>
      <div class="find2">
                    <h1>Find in the joined-table the rows of second table</h1>
                    <form name="find" action="find" method="post">
                           Name: <input type="text" name="name" required />	<br/>
                          <input type="submit" value="find" />
                    </form>
      </div>
      <div class='insert1'>
                  <h1>Add rows in second table</h1>
                  <form name="insert" action="insert" method="post">
                    User_id: <input type="text" name="user_id" required/>		<br/>
                    Name: <input type="text" name="name" required/>		<br/>
                    <input type="submit" value="Save"/>
                  </form>
      </div>
      <div class="insert2">
                    <h1>Add rows in first table</h1>
                    <form name="insert" action="insert" method="post">
                      Id_chat: <input type="text" name="id_chat" required/>		<br/>
                      User_id: <input type="text" name="user_id" required/>		<br/>
                      Cnt_slave: <input type="text" name="cnt_slave" required/>		<br/>
                      Cnt_master: <input type="text" name="cnt_master" required/>		<br/>
                      Bucks: <input type="text" name="bucks" required/>		<br/>
                      <input type="submit" value="Save"/>
                    </form>
      </div>
      <div class="delete1">
            <h1>delete from first table</h1>
              <form name="delete" action="delete" method="post">
                id: <input type="text" name="id1" required />	<br/>
                <input type="submit" value="Delete" />
              </form>
      </div>
      <div class="delete2">
            <h1>delete from second table</h1>
            <form name="delete" action="delete" method="post">
              id: <input type="text" name="id2" required />	<br/>
              <input type="submit" value="Delete" />
            </form>
      </div>
      <#if tableWithJoin??>
          <div class="tableJoin">
          <h1>Table with join</h1>
            <table class="datatable">
              <tr>
                  <th>id_chat </th>  <th> user_id </th>  <th> cnt_slave </th>  <th> cnt_master </th>  <th> bucks </th>  <th> name </th>
              </tr>
                  <#list tableWithJoin as row>
                      <tr>
                          <td>${row.id_chat}</td> <td>${row.user_id}</td> <td>${row.count_slave}</td> <td>${row.count_master}</td> <td>${row.bucks}</td> <td>${row.name}</td>
                      </tr>
                  </#list>
            </table>
         </div>
     <#else>
              <#if error_data??>
                    <div class="error_data">
                      <h1>Errors page:</h1>
                      <h3>${error_data}</h1>
                    </div>
              </#if>
     </#if>
     <div class='table1'>
      <h1>First table</h1>
        <table class="datatable">
          <tr>
              <th>id </th>  <th> id_chat </th>  <th> user_id </th>  <th> count_slave </th> <th> count_master </th> <th> bucks </th>
          </tr>
              <#list table1 as row>
                  <tr>
                      <td>${row.id}</td> <td>${row.id_chat}</td> <td>${row.user_id}</td> <td>${row.count_slave}</td> <td>${row.count_master}</td> <td>${row.bucks}</td>
                  </tr>
              </#list>
        </table>
     </div>
          <div class='table2'>
           <h1>Second table</h1>
             <table class="datatable">
               <tr>
                   <th>id </th>  <th> user_id </th>  <th> name </th>
               </tr>
                   <#list table2 as row>
                       <tr>
                           <td>${row.id}</td> <td>${row.user_id}</td> <td>${row.name}</td>
                       </tr>
                   </#list>
             </table>
          </div>
      <div class="clear">
            <h1>Refresh table</h1>
              <form name="delete" action="main" method="get">
                <input type="submit" value="Refresh" />
              </form>
      </div>
  </div>
</body>
</html>