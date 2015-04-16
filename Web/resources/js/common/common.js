	var replaceAll=function(value,source,target){
    	while(value.indexOf(source) > -1 ){ 
			value=value.replace(source,target);
		}
		return value;
    }
    var copyThis=function(dgt,rowIndex,column){
    	try{
			$(dgt).datagrid('selectRow', rowIndex);
			var rows = $(dgt).datagrid('getSelections');
			copyClip(rows[0][column]);
		}catch(e){
			alert(e.name);
		}
    }
   	var copyClip=function(txt){
    	try{
			if(window.clipboardData) {  
       			window.clipboardData.clearData();  
             	window.clipboardData.setData("Text", txt);  
     		} else if(navigator.userAgent.indexOf("Opera") != -1) {  
          		window.location = txt;  
     		} else if (window.netscape) {  
          		try {  
               		netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");  
          		} catch (e) {  
               		alert("被浏览器拒绝！\n请在浏览器地址栏输入'about:config'并回车\n然后将 'signed.applets.codebase_principal_support'设置为'true'");  
          		}  
          		var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard); 
		   		if (!clip)  
		 			return;  
				var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable); 
				if (!trans)  
					return;  
		       	trans.addDataFlavor('text/unicode');  
		 		var str = new Object();  
		 		var len = new Object();  
		 		var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);  
		  		var copytext = txt;  
				str.data = copytext;  
				trans.setTransferData("text/unicode",str,copytext.length*2);  
				var clipid = Components.interfaces.nsIClipboard;  
				if (!clip)  
		 			return false;  
				clip.setData(trans,null,clipid.kGlobalClipboard);  
			}  
			$.messager.alert('提示',"复制成功");
		}catch(e){
			alert(e.name);
		}
    }
    var loading=function(_msg){
    	if(_msg==null||_msg=='undefined')_msg='提交处理中...';
    	$.messager.progress({ 
			title:'请稍后', 
			msg:_msg
		}); 
    }
    var closeLoading=function(){
    	$.messager.progress('close');
    }
    var showMsg=function(msg,title,icon,fn){
    	if(title==null)title="提示"
    	$.messager.alert(title,msg,icon,fn);
    }
    var LabelRed=function(value){
		var sign_="<span class=s>* </span>";
		return sign_+value;
	}
	var openwin=function(params){
		var content = '<iframe scrolling="auto" frameborder="0" src="'+RootPath+params.url+'" style="width:100%;height:100%;overflow-x:hidden;overflow:auto;"></iframe>';
	   	$('#win').window({
	   		title: params.title,
		    width: params.width,
		    height: params.height,
		    content: content,
		    minimizable: false,
		    maximizable: false,
		    fit:params.fit,
		    draggable:false,
		    closable: true,
		    modal:true
		});
		$('#win').window('refresh');
		$('#win').window('center');
		$('#win').window('open');
	}