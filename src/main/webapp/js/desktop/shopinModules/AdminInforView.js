Ext.define("ShopinDesktop.AdminInforView",{
	extend:"Ext.grid.Panel",
	requires: [
		'ShopinDesktop.RoleResourceWindow',
		'ShopinDesktop.RoleUserWindow'
	],
	baseUrl: _appctx,
	tbar:null,
	columns:null,
	memberInforStore:null,
	itemsPerPage : 20,
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
		this.superclass.constructor.call(this, {
			columns: this.columns,
			tbar: this.tbar,
			selModel: this.sm,
//			bbar : this.pageBar,
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
		this.sm = Ext.create('Ext.selection.CheckboxModel',{
			singleSelect : true
			});
		var checkbox = this.sm;
		this.userInforStore = Ext.create("Ext.data.Store",{
			autoLoad : true,
			pageSize : this.itemsPerPage,
			fields : [
					"sid","roleName","roleCode","createdTime","updateTime","delFlag"
					],
 			proxy: {
						type: "ajax",
						url : _appctx + "/role/selectRoleByParam.json",
						getMethod: function(){
							return 'POST';
						},
						reader: {
							type: "json",
							root : "list",
						//	totalProperty :"totalCount"
						},
						listeners: {
							exception: function(proxy, response, operation, eOpts) {
								if (response.status != 200) {
									Ext.MessageBox.alert("Error", "加载列表失败！");
								}
							}
						}
				},
				listeners: {
					beforeload : function(store, operation, eOpts) {
						console.log(operation);
						this.getProxy().setExtraParam("roleCodeName",
							Ext.getCmp("roleCodes").getValue());
					}
				}
		});
		this.gender = function (val) {
	        if (val == 1) {
	            return  "男";
	        } else if (val == 2) {
	            return  "女";
	        }
	        return val;
    	};
    	this.type = function(val){
    		if (val == 1) {
	            return  "新注册";
	        } else if (val == 2) {
	            return  "升级会员";
	        }
	        return val;
    	}
		var pointsStore = this.userInforStore;
		this.columns=[
//						new Ext.grid.RowNumberer({
//							width:40
//						}),
			{
				header : "角色SID",
				dataIndex : "sid",
				width:50
				
			},
         	{
				header : "角色名称",
				dataIndex : "roleName",
				width:80
			
			},
			{
				header : "角色编码",
				dataIndex : "roleCode",
				width:80
			
			}
			,{
				header: "创建时间",
				dataIndex: "createdTime",
				width:150
			},{
				header: "修改时间",
				dataIndex: "updateTime",
				width:150
			},{
				header: "是否有效",
				dataIndex: "delFlag",
				width:60,
				renderer:function(value){
					if(value == 0){
						return "有效";
					}else{
						return "无效";
					}
				}	

			},{
				text:"禁用",
				xtype:'actioncolumn',
				sortable: true,
				align : 'center',
				width:50,
				items :[{	
						text:'禁用',
						xtype : 'button',
						tooltip: '禁用',
						icon:_appctx+'/images/remove.gif',
						handler:function(grid, rowIndex, colIndex){
						
							Ext.MessageBox.confirm('Confirm','你确定要进行此操作吗？',function (e){
								if('yes'==e){
									var allsid = checkbox.getSelection();
									if(allsid[0] != null){
										var sid = allsid[0].get("sid");
										Ext.Ajax.request({
											url: _appctx + "/role/disabledUse.json",
									    	params: {
									       		sid: sid
									    	},
									    	success: function(response){
									        	var response = Ext.JSON.decode(response.responseText);
									     		if(response.success){
									     			Ext.MessageBox.alert("提示","禁用成功！");
									     			Ext.getCmp('AdminInforView1').getStore().load();
									     		}else{
									     			Ext.MessageBox.alert("提示","禁用失败！");
									     		}
									        
									    	}
										});
									}else{
										Ext.MessageBox.alert("提示","请选中想要修改的一条数据");
									}
						
								}
							
							})
						}
					}]	
				},{
				text:"关联用户",
				xtype:'actioncolumn',
				sortable: true,
				align : 'center',
				width:70,
				items :[{	
						text:'关联用户',
						xtype : 'button',
						tooltip: '关联用户',
						icon:_appctx+'/images/edit.gif',
						handler:function(grid, rowIndex, colIndex){
								var allsid = checkbox.getSelection();
								var isUser = false;
								if(allsid[0] != null){
									var sid = allsid[0].get('sid');
									 var checkedUser = "";
									    //请求访问得到checkedUser
										Ext.Ajax.request({
									    	url: _appctx + "/roleUser/selecetCheckedUser.json",
									    	params: {
									       		roleSid: sid
									    	},
									    	success: function(response){
									        	var response = Ext.JSON.decode(response.responseText);
									        	checkedUser = response.obj;
										        Ext.create("ShopinDesktop.RoleUserWindow", {
													id: "roleUserWindow",
													baseUrl: this.baseUrl,
													caller: this,
													roleSid :sid,
													checkedUser: checkedUser
												}).show();
									    	}
										});
								}else{
									Ext.MessageBox.alert("提示","请选中想要修改的一条数据");
								}
						}
					}]	
				},{
				text:"关联资源",
				xtype:'actioncolumn',
				sortable: true,
				align : 'center',
				width:70,
				items :[{	
						text:'关联资源',
						xtype : 'button',
						tooltip: '关联资源',
						icon:_appctx+'/images/edit.gif',
						handler:function(grid, rowIndex, colIndex){
								var allsid = checkbox.getSelection();
								if(allsid[0] != null){
									var sid = allsid[0].get('sid');
									Ext.create("ShopinDesktop.RoleResourceWindow", {
											id: "roleResourceWindow",
											baseUrl: this.baseUrl,
											caller: this,
											roleSid :sid
										}).show();
								}else{
									Ext.MessageBox.alert("提示","请选中想要修改的一条数据");
								}
						}
					}]	
				}
				];
		this.tbar=Ext.create("Ext.toolbar.Toolbar",{
			id:"RoleInforTbar",
			items:[{
				xtype: "buttongroup",
				width:"100%",
				columns: 7,
				items:[
					{
					xtype : "textfield",
					id:"roleCodes",
					fieldLabel:"角色编码",
					labelAlign:"right",
					labelWidth: 70,
					width : 160,
				},
//				,{
//					xtype : "textfield",
//					id:"memberNames",
//					fieldLabel:"会员名",
//					labelAlign:"right",
//					labelWidth: 40,
//					width : 120,
//				},{
//					id : "memberMobile",
//					xtype : "textfield"	,
//					fieldLabel:"手机号",
//					labelAlign:"right",
//					labelWidth: 60,
//					width: 180
//				},
//				Ext.create("Util.DateTime",{
//						id: "createStartTime",
//						fieldLabel:"起始创建时间",
//						labelAlign: "right",
//						xtype: "datefield",
//						labelWidth: 80,
//						format: "Y-m-d h:m:s",
//				//		value: new Date(),
//						width:230
//				}),{
//					id: "createEndTime",
//					xtype: "datetimefield",
//					fieldLabel:"结束创建时间",
//					labelWidth: 80,
//					labelAlign: "right",
//					format: "Y-m-d h:m:s",
//				//	value: new Date(),
//					width:230
//				},
				{
					text:'查找',
					width:80,
					pressed: true,
					ctCls: 'x-btn-over',
					handler:function(){
						var memberSid = Ext.getCmp("roleCodes").getValue();
							pointsStore.load({
							params:{
								roleCodeName: memberSid
							},
						});
					}
				},{
					text:'重置',
					width:80,
					pressed: true,
					ctCls: 'x-btn-over',
					handler:function(){
						Ext.getCmp("roleCodes").setValue(null);	
					}
				},{
					text:'新建角色',
					width:80,
					pressed: true,
					ctCls: 'x-btn-over',
					handler:function(){
						Ext.create("ShopinDesktop.AddRoleInforWindow", {
							id: "addRoleInforWindow",
							baseUrl: this.baseUrl,
							caller: this,
						}).show();
					}
				}]
			}]
		}
		
		);

//		this.pageBar = Ext.create("Ext.toolbar.Paging", {
//			store : this.userInforStore,
//			displayInfo : true
//		});	

	},
	 listeners:{
		itemdblclick:function(view, record,item){
			if(record !=null){
				Ext.create("ShopinDesktop.UpdateRoleInforWindow", {
					id: "updateRoleInforWindow",
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