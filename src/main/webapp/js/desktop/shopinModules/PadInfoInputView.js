Ext.define("ShopinDesktop.PadInfoInputView",{
	extend:"Ext.grid.Panel",
	requires : [ 'Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	baseUrl:_appctx,
	tbar:null,
	username:username,
	columns:null,
	userAuthId:shopid,
	userAuthName:shopname,
	constructor:function(config){
		config = config || {};//保证了config能访问到属性，否则需要判断
		Ext.apply(this,config);
		this.initComponents();
		this.superclass.constructor.call(this,{
			id:"PadInfoInputView1",
			tbar: this.tbar,
			bbar : this.pageBar,
			store:this.padInfoInputStore,
			columns:this.columns,
			viewconfig:{
				enableTextSelection : true
			}
		});
		this.padInfoInputStore.load();
	},
	initComponents: function() {
		var thisView = this;
		var baseUrl = this.baseUrl;
		var required = '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>';
		var padPurchaseBatchNoStore = new Ext.data.Store({
        	autoLoad : true,
			proxy : {
				type : 'ajax',
				api : {
					read : _appctx + '/padPurchaseInfo/getPadPurchaseInfo.json'
				},
				reader : {
					type: 'json',
 					root: 'list',
				}
			},
			fields : ["padPurchaseBatchNo","padSupply","padPurchaseNum"]
    	});
		//根据选中的padPurchaseBatchNoStore中padPurchaseInfoCombo的值获取本批次已录入信息的pad个数
		this.padInfoInputStore = Ext.create("Ext.data.Store",{
			autoLoad:true,
			id : 'padInfoGridIdOfPIV',
			autoHeight:true,
			pageSize:20,
//			clearOnPageLoad:true,
			fields:["sid","padNo","padPurchaseBatchNo","macAddress","purchaseOrderno","brand","padStatus","createTime","shopName",
					"supplyName","useType","useTypeDesc","operator","brand","shopId"],//字段
			proxy:{
				type:"ajax",
				url:_appctx+"/padBaseinfo/padInfoInputList",
				getMethod:function(){
					return "POST";
				},
				reader:{
					type:"json",
					root:"obj.list",
					totalProperty:"total"
				},
				listeners:{
					exception: function(proxy, response, operation, eOpts) {
						if (response.status != 200) {
							Ext.MessageBox.alert("Error", "加载列表失败！");
						}
					}
				}
			},
			listeners:{
				beforeload:function(store, operation, eOpts){      //每次分页都携带参数！
						operation.params={padPurchaseBatchNo:Ext.getCmp("padPurchaseInfoCombo").getValue()};
				}}
		});
		this.columns=[
						new Ext.grid.RowNumberer({
									width:40
								}),
								{
									header : "padsid",
									dataIndex : 'sid',
									hidden : true,
								},
								{header:'编号',dataIndex:'padNo',width:120,sortable:true},
								{header:'批次号',dataIndex:'padPurchaseBatchNo',width:120,sortable:true},
								{header:'MAC地址',dataIndex:'macAddress',width:150,sortable:true},
								{header:'品牌',dataIndex:'brand',width:100,sortable:true},
								{header:'门店',dataIndex:'shopName',width:80,sortable:true},
//								{header:'绑定供应商名称',dataIndex:'supplyName',width:200,sortable:true},
								{header:'状态',dataIndex:'padStatus',width:60,sortable:true,
									renderer:function(value){
										if(value == 0) {
											return '在库';
										}
										if(value == 1) {
											return '卖场';
										}
										if(value == 2) {
											return '送修';
										}
										if(value == 3) {
											return '停用';
										}
										if(value == 4){
											return '丢失';
										}
										if(value == 5){
											return '在途';
										}
									}
								},
								{header:'使用类型',dataIndex:'useTypeDesc',width:80,sortable:true},
								{header:'操作人',dataIndex:'operator',width:80,sortable:true},
								{header:'创建时间',dataIndex:'createTime',width:150,sortable:true},
								{
									text:"修改",
									xtype:'actioncolumn',
									sortable: true,
									width:50,
									align:'center',
									items :[
										{
											text : '修改',
											tooltip: '修改',
											icon:_appctx+'/images/edit.gif',
											handler:function(grid, rowIndex, colIndex){
												var record = grid.getStore().getAt(rowIndex);
												updatePadBaseinfo(record);
											}
										}
									]	
								},
								{
									text:"查看安装列表",
									xtype:'actioncolumn',
									sortable: true,
									width:80,
									align:'center',
									items :[
										{
											text : '查看安装列表',
											xtype : 'button',
											tooltip: '查看安装列表',
											icon:_appctx+'/images/edit.gif',
											handler:function(grid, rowIndex, colIndex){
												var record = grid.getStore().getAt(rowIndex);
								  				macAddress = record.data.macAddress;
								  				shopId = record.data.shopId;
												watchPadSoft();
											}
										}
									]	
								}
				];
		this.selModel=Ext.create('Ext.selection.CheckboxModel',{
			singleSelect : true
		});
		var checkBox = this.selModel;
		//bar
		var padinfoStore = this.padInfoInputStore;
		this.tbar=Ext.create("Ext.toolbar.Toolbar",{
			id:"padInfoInputTbarOfPIV",
			items:[{
				xtype: "buttongroup",
				width:"100%",
				columns: 5,
				items:[
				    {
						xtype:"combo",
						fieldLabel : '按批次批量查询',
						labelWidth: 100,
						labelAlign:"right",
						width :300,
						id:"padPurchaseInfoCombo",
						store:padPurchaseBatchNoStore,
						valueField: 'padPurchaseBatchNo',//具体值
						displayField:"padPurchaseBatchNo",//显示的值
						triggerAction : 'all',
						typeAhead: true,
						queryMode: "remote",//每次都远程查找
						autoLoad:false,
						listeners : {
						     beforequery: function(qe){
						      delete qe.combo.lastQuery;
						     }
						}
				    },{
						text:'添加',
						width:80,
						pressed: true,
						ctCls: 'x-btn-over',
						handler:function(){
							Ext.create("ShopinDesktop.PadBaseinfoAddWindow", {
								id: "padBaseinfoAddId",
								baseUrl: this.baseUrl,
								caller: this
							}).show();
						}
					},{
						text:'导入',
						width:80,
						pressed: true,
						ctCls: 'x-btn-over',
						handler:function(){//TODO
							var padPurchaseBatchNo = Ext.getCmp("padPurchaseInfoCombo").getValue();
					    	if(""==padPurchaseBatchNo||null==padPurchaseBatchNo){
					    		Ext.Msg.alert('提示','请选择批次号！');
					    		return;
					    	}
							win_upload.show();
						}
					},{
						text:'查找',
						width:80,
						pressed: true,
						ctCls: 'x-btn-over',
						handler:function(){
							padinfoStore.load({  
							 	 params:{
							 		padPurchaseBatchNo : Ext.getCmp("padPurchaseInfoCombo").getValue()
							 	 }
							});
						}
					},{
						text:'删除',
						width:80,
						pressed: true,
						ctCls: 'x-btn-over',
						handler:function(){
							var sidArr = checkBox.getSelection();
							var sids = "";
							for(var i=0;i<sidArr.length;i++){
								sids = sids + sidArr[i].get('sid') +",";
							}
							if(sids!=null&&sids!=""){
								deletePadInfo(sids);
							}else{
								Ext.Msg.alert('信息提示','请选择要删除项');
							}
						}
					}
					]}]
			});
		//分页bar
		this.pageBar = Ext.create("Ext.toolbar.Paging", {
			store : this.padInfoInputStore,
			displayInfo : true
		});
		this.padInfoInputStore.load();
		function updatePadBaseinfo(record){
			  Ext.create('ShopinDesktop.PadInfoUpdateWindowOfPIV',{
				    record : record
				}).show();
		 }
		 function boundSupply(){
			Ext.create('ShopinDesktop.PadBoundSupplyWindow',{
						    padNo : padNo
						}).show();
		 }
		 function watchPadSoft(){
			Ext.create('ShopinDesktop.WatchPadSoftWindow',{
						    macAddress:macAddress,
						    shopId:shopId
						}).show();
		 }
		//删除
		function deletePadInfo(sids){
			Ext.Msg.show({
				title : '提示消息',
				icon : Ext.MessageBox.QUESTION,
				msg : '确认删除?',
				buttons : Ext.MessageBox.YESNO,
				fn : function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : _appctx + "/padBaseinfo/deletePadInfoBySid.json",
							_method : 'POST',
							params : {
//								_method : "POST",
								sid :sids,
								username:username,
								userSid:userSid
							},
							 success:function(form) {
									Ext.Msg.alert('提示', '删除成功');
									//刷新
									Ext.StoreMgr.get("padPurchaseInfoStoreOfPPV").reload();  //入库刷新
									padinfoStore.load({  
										params:{
											padPurchaseBatchNo : Ext.getCmp("padPurchaseInfoCombo").getValue()
										}
									});
							},
							failure : function() {
								Ext.Msg.alert('信息提示','删除失败，请与管理员联系');
							}
			     });
					}
				}
				
			});
		}
		//TODO
		/*var form = new Ext.form.FormPanel({
		     baseCls : 'x-plain',
		     id : 'uploadForm111',
		     labelWidth : 70,
		     labelHeight: 150,
		     fileUpload : true,
		     defaultType : 'textfield',
		     items : [{
		        xtype : 'textfield', 
		        fieldLabel : '选择文件',
		        name : 'padExcelUpload',
		        id : 'padExcelUpload',
		        inputType : 'file',
		        anchor : '95%'
		       }]  
		});*/
		/*var form = Ext.create('Ext.form.Panel', {
		     baseCls : 'x-plain',
		     id : 'uploadForm111',
		     labelWidth : 70,
		     labelHeight: 150,
		     fileUpload : true,
		     defaultType : 'textfield',
		     items : [{
		        xtype : 'textfield', 
		        fieldLabel : '选择文件',
		        name : 'padExcelUpload',
		        id : 'padExcelUpload',
		        inputType : 'file',
		        anchor : '95%'
		       }]  
		});*/
		var win_upload = new Ext.Window({
		    title : '导入PAD信息',  
		    width :450,
		    height : 120,
		    modal : true,
		    x : 450,  
		    y : 200,  
		    layout : 'fit',  
		    autoScroll : true,  
		    constrain : true,  
		    bodyStyle : 'padding:10px 10px 10px 10px;',
		    closable: false,  
		    draggable: false,  
		    resizable: false, 
		    items:[{
		    	xtype: 'form',
		    	baseCls : 'x-plain',
			    id : 'uploadForm',
			    labelWidth : 70,
			    labelHeight: 150,
			    fileUpload : true,
			    defaultType : 'textfield',
			    items : [{
			       xtype : 'filefield', 
			       fieldLabel : '选择文件',
			       name : 'padExcelUpload',
			       id : 'padExcelUpload',
//			       inputType : 'file',
			       anchor : '95%'
			      }] 
		    }],
		    buttons : [{
		       text : '确认',
		       handler : function() {
		       var padPurchaseBatchNo = Ext.getCmp("padPurchaseInfoCombo").getValue();
		       var form = this.up('window').down('form');
		       if (form.form.isValid()) {
		    	   Ext.Msg.wait("正在拼命上传文件，请等待");
		    	   form.getForm().submit({
			           url : baseUrl + "/padBaseinfo/importExcelOfPad.json?padPurchaseBatchNo="+padPurchaseBatchNo+"&shopid="+shopid+"&username="+username+"&userSid="+userSid,
			           method : 'POST',
			           success : function(form, action) {
			        	   win_upload.hide();
			        	   if(action.result.success == "true"){
								Ext.Msg.alert('提示',"恭喜！文件上传成功！");
								Ext.StoreMgr.get("padInfoGridIdOfPIV").reload();  //录入刷新
								Ext.StoreMgr.get("padBaseInfoGridIdOfPCV").reload();   //查看刷新
								Ext.StoreMgr.get("padBaseInfoGridIdOfPAV").reload();   //调拨刷新
								Ext.StoreMgr.get("padPurchaseInfoStoreOfPPV").reload();  //入库刷新
							}else if(action.result.success == "false"){
								if(action.result.errorCode == "10000"){
									Ext.Msg.alert('错误',action.result.memo);
								}else{
									Ext.Msg.alert('错误',"导入失败！");
								}
							}
			           },  
			           failure : function(form, action) {
			               Ext.Msg.alert('错误',"文件上传失败，请重新操作！");  
			         } 
			        });
		       }  
		      }  
		    }, {
		        text : '取消',  
		        handler : function() {  
		            win_upload.hide();  
		        }  
		    }] 
		});
		
	},
	listeners:{
		itemdblclick:function(view, record,item){
			
		}
	}
	
});