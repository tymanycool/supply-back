/**
 * 导购登录信息管理
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.GuideLogininfoManageWindow', {
	extend:"Ext.ux.desktop.Module",
	requires : ['Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	baseUrl: _appctx,
	id : 'guideLogininfoManageWindow',
	tbar:null,
	columns:null,
	guideInfoStore:null,
	guideNo : null,
	shopName : null,
	shopId : null,
	chestcardShoprule : null,
	userName : null,
	password : null,
	sid : null,
	itemsPerPage : 20,
	init : function(){
//		this.launcher = {
//			text: '导购登陆信息管理',
//			iconCls:'icon-grid'
//		};
	},
	createWindow : function() {
		var me = this;
		this.initComponents(me);
		var desktop = this.app.getDesktop();
		var win = desktop.getWindow(me.id);
		if (!win) {
			win = desktop.createWindow({
				id : me.id,
				title : '导购登录信息管理',
				width : 700,
				height : 580,
				animCollapse : false,
				constrainHeader : true,
				layout : 'fit',
				items : [ {
					items : me.gridPanel
				} ],
				bbar : {
					xtype : 'pagingtoolbar',
					store : this.guideLogininfoStore,
					displayInfo : true
				}
			});
		}
		return win;
	},
	initComponents: function(me) {
		var thisView = this;
		var baseUrl = this.baseUrl;
		
		this.sm = Ext.create('Ext.selection.CheckboxModel',{
			singleSelect : true
			});
		var checkbox = this.sm;
		
		this.guideLogininfoStore = Ext.create("Ext.data.Store",{
			autoLoad:false,
			pageSize : 20,
			clearOnPageLoad : true,
			fields : [
					"sid","guideNo","shopId","shopName","chestcardShoprule","chestcardNum","loginUsername","loginPassword","creattime"
					],
 			proxy: {
					type: "ajax",
					url : _appctx + '/guideLogininfo/list',
					getMethod: function(){
						return 'POST';
					},
					reader: {
						type: "json",
						root : "obj.list",
						totalProperty :"obj.total"
					},
					listeners: {
						exception: function(proxy, response, operation, eOpts) {
							if (response.status != 200) {
								Ext.MessageBox.alert("Error", "加载列表失败！");
							}
						}
					}
				}
		});
		
		var pointsStore = this.guideLogininfoStore;
		this.gridPanel = Ext.create('Ext.grid.Panel', {
			id : 'guideLogininfoGridPanel',
			loadMask:{msg : '数据加载中...'},
			width : 700,
			height : 580,
			columns : [
				{
						dataIndex : 'sid',
						hidden : true,
						hideable : false
					}, 
					{header:'导购编号',dataIndex:'guideNo',hidden : true,hideable : false},
					{header:'门店名称',dataIndex:'shopName',width:80,sortable:true},
					{header:'胸卡编号',dataIndex:'chestcardShoprule',width:80,sortable:true},
					{header:'用户名',dataIndex:'loginUsername',width:80,sortable:true},
					{header:'密码',dataIndex:'liginpassword',width:80,sortable:true,
						renderer:function(value){ 
							if(value == ""){
								return "";
							}else{
						   		return "*****";
							}
						}},
					{header:'创建时间',dataIndex:'creattime',width:150,sortable:true}
//						,
//					{
//						text:"操作",
//						xtype:'actioncolumn',
//						sortable: true,
//						width:100,
//						align:'center',
//						items :[
////							{
////							text : '修改',
////							xtype : 'button',
////							tooltip: '修改',
////							icon:_appctx+'/images/edit.gif',
////							handler:function(grid, rowIndex, colIndex){
////								var allsid = checkbox.getSelection();
////								if(allsid[0] == undefined){
////									Ext.MessageBox.alert("提示","请选中一条数据");
////									return;
////								}
////								
////				  				sid = allsid[0].get('sid');
////				  				guideNo = allsid[0].get('guideNo');
////				  				shopName = allsid[0].get('shopName');
////				  				shopId = allsid[0].get('shopId');
////				  				chestcardShoprule = allsid[0].get('chestcardShoprule');
////				  				userName = allsid[0].get('userName');
////				  				password = allsid[0].get('password');
////								updateGuideLogininfo(sid);
////								}
////							},
//							{
//								xtype : 'button',
//								width:20
//							},
//							{
//							text : '删除',
//							xtype : 'button',
//							tooltip: '删除',
//							icon:_appctx+'/images/remove.gif',
//							handler:function(grid, rowIndex, colIndex){
//								var allsid = checkbox.getSelection();
//								if(allsid[0] == undefined){
//									Ext.MessageBox.alert("提示","请选中一条数据");
//									return;
//								}else{
//									Ext.Msg.confirm("提示","确认要删除该导购信息？",function(button){
//										if(button=="yes"){
//											sid = allsid[0].get('sid');
//											delGuideLogininfo(sid);
//										}
//									});
//								}
//								}
//							}
//						]	
//					}
				],
			store:this.guideLogininfoStore,  
			columnLines : true,
			selModel: this.sm,
			tbar : [{
				xtype: "buttongroup",
				width:"100%",
				columns: 7,
				items:[
					{
						xtype : "textfield",
						id:"userName",
						fieldLabel:"用户名",
						labelAlign:"right",
						labelWidth: 60,
						width : 140,
					},
					{
						text:'查找',
						width:80,
						pressed: true,
						ctCls: 'x-btn-over',
						handler:function(){
							pointsStore.load({
								params:{
									userName : Ext.getCmp('userName').getValue(),
									start:0
								},
							});
						}
					}]
			}],
			sortableColumns : false
	});
		
		/**
		 * 修改
		 */
		function updateGuideLogininfo(){
			  Ext.create('ShopinDesktop.GuideLogininfoUpdateWindow',{
				    sid : sid,
					guideNo : guideNo,
					shopName : shopName,
					shopId : shopId,
					chestcardShoprule : chestcardShoprule,
					userName : userName,
					password : password
				}).show();
		 }
		
		/**
		 * 删除
		 * @param {Object} sid
		 */
		function delGuideLogininfo(sid){
			Ext.Ajax.request({
				url : _appctx + '/guideLogininfo/updateGuidelLoginValidStatus',
				method:'POST',
				params: { sid: sid},
				success: function(response){
					var result = Ext.decode(response.responseText);
					if(result.success=="true"){
						Ext.Msg.alert('','删除成功');
						me.guideLogininfoStore.reload();
					}else{
						Ext.Msg.alert('错误','删除失败！'+result.msg);
					}
				},
				failure: function(){
					Ext.Msg.alert('','删除失败，请与管理员联系');
				}
				})
		 }
		
		this.pageBar = Ext.create("Ext.toolbar.Paging", {
			store : this.guideLogininfoStore,
			displayInfo : true
		});
		
		this.guideLogininfoStore.loadPage(1);	
	}
});
