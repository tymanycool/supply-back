Ext.define("ShopinDesktop.UserInforView",{
	extend:"Ext.grid.Panel",
	baseUrl: _appctx,
	tbar:null,
	columns:null,
	memberInforStore:null,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			id : 'UserInforView1',
			columns: this.columns,
			tbar: this.tbar,
			bbar : this.pageBar,
			store: this.userInforStore,
			viewConfig : {
				enableTextSelection : true
			// 启用文字选择
			}
		});
	},
	initComponents: function() {
		var thisView = this;
		var baseUrl = this.baseUrl;
		this.userInforStore = Ext.create("Ext.data.Store",{
			autoLoad : true,
			pageSize : 20,
			clearOnPageLoad : true,
			fields : [
				   "sid","username","userPssword","sex","spell","mobile","shopSid","shopName","validBit","createtime","userCode","operator"
					],
 			proxy: {
				type: "ajax",
				url : _appctx + "/systemUser/selectSystemUserByParams.json",
				getMethod: function(){
					return 'POST';
				},
				reader: {
					type: "json",
					root : "list",
					totalProperty :"total"
				},
				listeners: {
					exception: function(proxy, response, operation, eOpts) {
						if (response.status != 200) {
							Ext.MessageBox.alert("Error", "加载列表失败！");
						}
					}
				}
			}
//		,
//			listeners: {
//				beforeload : function(store, operation, eOpts) {
//					console.log(operation);
//					this.getProxy().setExtraParam("userCodeName",
//						Ext.getCmp("userCodes").getValue());
//				}
//			}
		});
		this.gender = function (val) {
	        if (val == 1) {
	            return  "男";
	        } else if (val == 2) {
	            return  "女";
	        }
	        return val;
    	};
    	
		var pointsStore = this.userInforStore;
		this.columns=[
				new Ext.grid.RowNumberer({
							width:40
						}),
				{
					header : "用户SID",
					dataIndex : "sid",
					hidden : true,
					width:120
				},
				{
					header : "真实姓名",
					dataIndex : "username",
					width:100
				
				},
				{
					header : "登录名",
					dataIndex : "userCode",
					width:120
				
				},
//				{
//					header: "性别",
//					dataIndex: "sex",
//					renderer:function(v){
//						if(v == '0'){
//							return "男";
//						}else{
//							return "女";
//						}
//					},
//					width:80
//					
//				},
				{
					header: "所属门店",
					dataIndex: "shopSid"
//							renderer:function(v){
//								var show = "";
//								var index = shopStore.find('sid',v);
//								show = shopStore.getAt(index).get("shopName");
//								return show;
//							}
				},{
					header: "所属门店名称",
					dataIndex: "shopName"
				},{
					header: "创建时间",
					dataIndex: "createtime",
					width:130
				},{
					header: "是否有效",
					dataIndex: "validBit",
					renderer:function(v){
						if(v == '0'){
							return "无效";
						}else{
							return "有效";
						}
					}	

				},{//add by qutengfei for 优化 用户操作时显示操作人  https://tower.im/s/a9DfD in20150819 start
					header: "操作人",
					dataIndex: "operator",
					width:130
				}
				 	//add by qutengfei for 优化 用户操作时显示操作人  https://tower.im/s/a9DfD in20150819 end
//				,{
//					text:"删除",
//					xtype:'actioncolumn',
//					sortable: true,
//					width:40,
//					align:'center',
//					items :[
//						{
//						text : '删除',
//						xtype : 'button',
//						tooltip: '删除',
//						icon:_appctx+'/images/remove.gif',
//						handler:function(grid, rowIndex, colIndex){
//							
//							var record = grid.getStore().getAt(rowIndex);  
//							
//							Ext.Msg.confirm("提示","确认要删除该用户信息？",function(button){
//								if(button=="yes"){
//									sid = record.data.sid;
//									deluserinfo(sid);
//								}
//							});
//							}
//						}
//					]	
//					}
		];
		
		function deluserinfo(sid){
			Ext.Ajax.request({
				url : _appctx + '/systemUser/deleteSystemUser',
				method:'POST',
				params: { 
					sid : sid,
					username : username
					},
				success: function(response){
					var result = Ext.decode(response.responseText);
					if(result.success=="true"){
						Ext.Msg.alert('','删除成功');
						thisView.userInforStore.reload();
					}else{
						Ext.Msg.alert('错误','删除失败');
					}
				},
				failure: function(){
					Ext.Msg.alert('','删除失败，请与管理员联系');
				}
				})
		 }
		
		this.tbar=Ext.create("Ext.toolbar.Toolbar",{
			id:"UserInforTbar",
			items:[{
				xtype: "buttongroup",
				width:"100%",
				columns: 7,
				items:[
					{
					xtype : "textfield",
					id:"userCodeId",
					fieldLabel:"登录名",
					labelAlign:"right",
					labelWidth: 70,
					width : 160
				},
				{
					text:'查找',
					width:80,
					pressed: true,
					ctCls: 'x-btn-over',
					handler:function(){
						var userCode = Ext.getCmp("userCodeId").getValue();
						pointsStore.load({
						params:{
							userCode: userCode
						}
						});
					}
				},{
					text:'重置',
					width:80,
					pressed: true,
					ctCls: 'x-btn-over',
					handler:function(){
						Ext.getCmp("userCodes").setValue(null);	
					}
				},{
					text:'新建用户',
					width:80,
					pressed: true,
					ctCls: 'x-btn-over',
					handler:function(){
						Ext.create("ShopinDesktop.AddUserInforWindow", {
							id: "addUserInforWindow",
							baseUrl: this.baseUrl,
							caller: this
						}).show();
					}
				}]
			}]
		});
		
		this.pageBar = Ext.create("Ext.toolbar.Paging", {
			store : this.userInforStore,
			displayInfo : true
		});	

	},
	 listeners:{
		itemdblclick:function(view, record,item){
			if(record !=null){
				Ext.create("ShopinDesktop.UpdateUserInforWindow", {
					id: "updateUserInforWindow",
					baseUrl: this.baseUrl,
					caller: this,
					record: record
				}).show();
			}else{
				Ext.MessageBox.alert("提示","请选择一条记录");
			}
		}
	}

	
});