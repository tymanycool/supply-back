/**
 * PAD收货查询
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.PadReceiveView', {
	extend:"Ext.window.Window",
	requires : ['Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	baseUrl: _appctx,
	tbar:null,
	columns:null,
	padBaseInfoStore:null,
	padNo : null,
	macAddress : null,
	purchaseOrderno : null,
	padStatusText : null,
	sid : null,
	userAuthId:shopid,//获取登录用户所处门店
	userAuthName:shopname,
	id : 'PadReceiveViewId',
	constructor: function(config) {
		config = config || {
		};
		Ext.apply(this, config);
		var me = this;
		this.initComponents(me);
		this.superclass.constructor.call(this, {
			width : 1300,
			height : 580,
			modal: true,
			constrain: true,
			layout: "border",
			title: "PAD收货",
			items : [this.gridPanel],
			animCollapse : false,
			constrainHeader : true,
			layout : 'fit',
			bbar : {
				xtype : 'pagingtoolbar',
				store : this.padBaseInfoStore,
				displayInfo : true
			}
		});
	},
	initComponents: function(me) {
		var thisView = me;
		var baseUrl = this.baseUrl;
		
		this.sm = Ext.create('Ext.selection.CheckboxModel',{
			singleSelect : true
			});
		var checkbox = this.sm;
		this.padBaseInfoStore = Ext.create("Ext.data.Store",{
			id : 'padBaseInfoGridIdOfPRV',
			autoLoad : true,
			pageSize : 20,
			fields : [
					"sid","padNo","macAddress","purchaseOrderno","brand","padStatus","createTime","shopName","targetShopId","targetShopName",
					"supplyName","useType","useTypeDesc","operator","brand","shopId"
					],
 			proxy: {
					type: "ajax",
					url : _appctx + '/padBaseinfo/getPadReceiveInfo',
					getMethod: function(){
						return 'POST';
					},
					reader: {
						type: "json",
						root : "obj.list",
						totalProperty :"total"
					},
					listeners: {
						exception: function(proxy, response, operation, eOpts) {
							if (response.status != 200) {
								Ext.MessageBox.alert("Error", "加载列表失败！");
							}
						}
					}
				},
			listeners:{
				beforeload: function(store, operation, eOpts) {
						Ext.apply(store.proxy.extraParams, {
							targetShopId : userAuthId,
				        });
					},
				load: function(store, records, success, eOpts) {
				}
			}
		});
		
		var padinfoStore = this.padBaseInfoStore;
		
		/**
		 * 修改
		 */
		 function updatePadBaseinfo(record){
			  Ext.create('ShopinDesktop.PadBaseinfoUpdateWindow',{
				    record : record
//					padNo: padNo,
//					macAddress : macAddress,
//					purchaseOrderno : purchaseOrderno,
//					padStatus : padStatusText
				}).show();
		 }
		 
		 /**
		  * 绑定供应商
		  */
		 function boundSupply(){
			Ext.create('ShopinDesktop.PadBoundSupplyWindow',{
						    padNo : padNo
						}).show();
		 }
		 //add by qutengfei for 查看pad安装列表 in 20150723 start
		/**
		  * 查看pad安装列表
		  * for bug
		  * feature https://tower.im/s/9cCdh
		  */
		 function watchPadSoft(){
			Ext.create('ShopinDesktop.WatchPadSoftWindow',{
						    macAddress:macAddress,
						    shopId:shopId
						}).show();
		 }
		 //add by qutengfei for 查看pad安装列表 in 20150723 end
		 
		var userAuthId = this.userAuthId;
		var userAuthName = this.userAuthName;
		this.gridPanel = Ext.create('Ext.grid.Panel', {
			id : 'padBaseinfoGridPanelOfPRV',
			loadMask:{msg : '数据加载中...'},
			width : 1000,
			height : 580,
			columns : [
					{
						dataIndex : 'sid',
						hidden : true,
						hideable : false
					}, 
					{header:'编号',dataIndex:'padNo',width:120,sortable:true},
					{header:'MAC地址',dataIndex:'macAddress',width:150,sortable:true},
					{header:'品牌',dataIndex:'brand',width:100,sortable:true},
//					{header:'采购订单号',dataIndex:'purchaseOrderno',width:100,sortable:true},
//					{header:'PAD品牌',dataIndex:'brand',width:100,sortable:true},
					{header:'门店',dataIndex:'shopName',width:80,sortable:true},
					{header:'目的门店',dataIndex:'targetShopName',width:80,sortable:true},
					{header:'绑定供应商名称',dataIndex:'supplyName',width:200,sortable:true},
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
						hidden:true,
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
						text:"绑定供应商",
						xtype:'actioncolumn',
						sortable: true,
						width:80,
						hidden:true,
						align:'center',
						items :[
							{
								text : '绑定供应商',
								xtype : 'button',
								tooltip: '绑定供应商',
								icon:_appctx+'/js/desktop/images/contact_blue_new.png',
								handler:function(grid, rowIndex, colIndex){
									var record = grid.getStore().getAt(rowIndex);
					  				padNo = record.data.padNo;
									boundSupply();
								}
							}
						]	
					},
					//add by qutengfei for 查看pad安装列表 in 20150723 start
					/**
					  * 查看pad安装列表
					  * for bug
					  * feature https://tower.im/s/9cCdh
					  * author qutengfei
					  */
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
					//add by qutengfei for 查看pad安装列表 in 20150723 end
				],
			store: this.padBaseInfoStore,  
			columnLines : true,
			selModel: this.sm,
			tbar:[
				{
					xtype: "buttongroup",
					width:"100%",
					columns: 8,
					items:[
						{
							text:'确认收货',
							width:80,
							pressed: true,
							ctCls: 'x-btn-over',
							margin : '0 10 0 25',
							handler:function(){
								var padIdArr = checkbox.getSelection();
								var padIds = "";
								for(var i=0;i<padIdArr.length;i++){
									padIds = padIds + padIdArr[i].get('padNo') +",";
								}
								if(padIds!=null&&padIds!=""){
									Ext.Ajax.request({
										url:_appctx +'/padBaseinfo/savePadReceiveInfo?userAuthName='+username+"&userAuthId="+userSid,
										//参数可以为JSON对象或键值对
										params:{padIds:padIds},
										success:function(response){
											var result = Ext.JSON.decode(response.responseText);
											var isSuccess = result.success ;
											if(isSuccess == "true"){
												Ext.Msg.alert('成功',"收货成功！");
												//更新grid的store
												Ext.StoreMgr.get("padBaseInfoGridIdOfPRV").reload();
												Ext.StoreMgr.get("padBaseInfoGridIdOfPAV").reload();
												
											}else if(isSuccess == "false"){
												Ext.Mag.alert("提示",result.memo);
											}
										},
										failure:function(response){
											Ext.Msg.alert('失败',"操作失败！");
										}
									});
								}else{
									Ext.Msg.alert('信息提示','请选择要收货的Pad!');
								}
								
								
//								 padinfoStore.load({  
//								 	 params:{
//								 		shopId : userAuthId!='1000'?userAuthId:Ext.getCmp('padShopIdOfPRV').getValue(),
//										supplyId : Ext.getCmp('padSupplySidOfPRV').getValue(),
//										padNo : Ext.getCmp('padNoIdOfPRV').getValue(),
//										macAddress : Ext.getCmp('macAddressIdOfPRV').getValue(),
//										padStatus : Ext.getCmp('padStatusIdOfPRV').getValue(),
//										useType : Ext.getCmp('useTypeIdOfPRV').getValue()
//									}
//								});  
							}
						}
					]
				}
			],
			sortableColumns : false
		});
		this.pageBar = Ext.create("Ext.toolbar.Paging", {
			store : this.padBaseInfoStore,
			displayInfo : true
		});
	},
});
