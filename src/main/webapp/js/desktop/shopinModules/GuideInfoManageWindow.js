/**
 * 导购基本信息管理
 * @param {Object} config
 * @memberOf {TypeName} 
 */
Ext.define('ShopinDesktop.GuideInfoManageWindow', {
	extend:"Ext.ux.desktop.Module",
	requires : ['Ext.grid.Panel' ,'Ext.data.Model','Ext.data.Store'],
	baseUrl: _appctx,
	id : 'guideInfoManageWindow',
	tbar:null,
	columns:null,
	guidInfoStore:null,
	inite : function(){
//		this.launcher = {
//			text: '导购信息管理',
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
				title : '导购基本信息管理',
				width : 1200,
				height : 580,
				animCollapse : false,
				constrainHeader : true,
				layout : 'fit',
				items : [me.gridPanel],
				bbar : {
					xtype : 'pagingtoolbar',
					store : this.guideInfoStore,
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
		
		var validBitCombo = new Ext.data.ArrayStore({
			fields: ['validBitCode','value'],
			data : [
			['-1',"全部"],['0',"无效"],['1',"有效"]
			]
		});
		
		var guideStatusCombo = new Ext.data.ArrayStore({
			fields: ['guideStatusCode','value'],
			data : [
				['-1',"全部"],['0',"初始状态"],['1',"在柜"],['2',"不在柜"]
			]
		});
		
		var shopStore = new Ext.data.JsonStore({
			autoLoad : true,
			proxy : {
				type : 'ajax',
				api : {
					read : _appctx + '/guideSupply/getShopsList'
				},
				idParam : 'sid',
				reader : {
					type : 'json',
					root : 'result'
				}
			},
			fields: ["sid", "shopName"]
			
		});
		
		this.guideInfoStore = Ext.create("Ext.data.Store",{
//			autoLoad : true,
			pageSize : 20,
			clearOnPageLoad : true,
			fields : [
					"sid","guideNo","chestNum","name","age","stature","address","presentAddress","education","educationCartNum","kitasNum",
					"kitasEndtime","healthCartNum","healthCartEndtime","spell","sex","mobile","email","guideCard","guideBit","chestBit",
					"depositBit","depositNum","entrytime","leavetime","validBit","operator","createtime","guideStatus","chestcardNumber",
					"supplyId","supplyName","categroys","brand","shopName","flag","endTime","customerFlag"
					],
 			proxy: {
					type: "ajax",
					url : _appctx + '/guideinfo/list',
					timeout:180000,
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
							chestcardNumber : Ext.getCmp('chestcardNumberId').getValue(),
							supplyId : Ext.getCmp('supplyInfoId').getValue(),
							validBit : Ext.getCmp('guideinfoValidBit').getValue(),
							guideStatusId : Ext.getCmp('guideinfoStatus').getValue(),
							guideName : Ext.getCmp('guideNameId').getValue(),
							spell : Ext.getCmp('spellId').getValue(),
							shop : Ext.getCmp('shopGuideManageSid').getValue()
				        });
					},
				load: function(store, records, success, eOpts) {
				}
			}
		});
		
		this.gridPanel = Ext.create('Ext.grid.Panel', {
			plugins:[
                 Ext.create('Ext.grid.plugin.CellEditing',{
                     clicksToEdit:1 //设置单击单元格编辑
                 })
	      		],
			id : 'guideInfoGridPanel',
			loadMask:{msg : '数据加载中...'},
			width : 1200,
			height : 580,
			columns : [
					 new Ext.grid.RowNumberer(),
					{
						dataIndex : 'sid',
						hidden : true,
						hideable : false
					}, 
					{header:'导购编号',dataIndex:'guideNo',id : 'sidId',hidden : true,hideable : false},
//					{header:'导购胸卡编号',dataIndex:'chestNum',width:80,sortable:true},
					{header:'姓名',dataIndex:'name',width:80,sortable:true},
//					{header:'姓名拼音',dataIndex:'spell',width:80,sortable:true},
					{header:'胸卡编号',dataIndex:'chestcardNumber',width:80,sortable:true,editor:new Ext.form.TextField()},
					{header:'门店名称',dataIndex:'shopName',width:80,sortable:true},
					{header:'性别',dataIndex:'sex',width:40,sortable:true},
					{header:'手机号码',dataIndex:'mobile',width:90,sortable:true,editor:new Ext.form.TextField()},
					{header:'身份证号',dataIndex:'guideCard',width:150,sortable:true},
					{header:'是否是导购',dataIndex:'guideBit',width:70,sortable:true,
						renderer:function(value){
							if(value == 0||value ==2) {
								return '不是';
							}
							if(value == 1) {
								return '是';
							}
						}
					},
					{header:'导购状态',dataIndex:'guideStatus',width:80,sortable:true,
						renderer:function(value){
							if(value == 0) {
								return '初始状态';
							}
							if(value == 1) {
								return '在柜';
							}
							if(value == 2) {
								return '不在柜';
							}
						}
					},
					{header:'是否有效',dataIndex:'validBit',width:60,sortable:true,
						renderer:function(value){
							if(value == 0) {
								return '无效';
							}
							if(value == 1) {
								return '有效';
							}
						}
					},
					{header:'操作人',dataIndex:'operator',width:70,sortable:true},
					{header:'是否开通手动变价权限',dataIndex:'flag',width:140,sortable:true,
						renderer:function(value){
							if(value == 0) {
								return '否';
							}
							if(value == 1) {
								return '是';
							}
						}
					},
					{header:'是否开通导购退货支付权限',dataIndex:'customerFlag',width:140,sortable:true,
						renderer:function(value){
							if(value == 0||value==null) {
								return '否';
							}
							if(value == 1) {
								return '是';
							}
						}
					},
					{header:'权限截止时间',dataIndex:'endTime',width:90,sortable:true},
					{header:'创建时间',dataIndex:'createtime',width:140,sortable:true},
					{
						text:"修改",
						xtype:'actioncolumn',
						sortable: true,
						width:40,
						align:'center',
						items :[
							{
							text : '修改',
							xtype : 'button',
							tooltip: '修改',
							icon:_appctx+'/images/edit.gif',
							handler:function(grid, rowIndex, colIndex){
								
							/*	if(roleUser.indexOf("91") > -1 )
								{
								    Ext.Msg.alert("提示","没有此权限！");
								    return;
								}*/
								
								var record = grid.getStore().getAt(rowIndex); 
								
								updateGuideinfo(record);
								}
							}
						]	
					},
					{
						text:"绑定供应商",
						xtype:'actioncolumn',
						sortable: true,
						width:70,
						align:'center',
						items :[
							{
							text : '绑定供应商',
							xtype : 'button',
							tooltip: '绑定供应商',
							icon:_appctx+'/js/desktop/images/contact_blue_new.png',
							handler:function(grid, rowIndex, colIndex){
								

						/*		if(roleUser.indexOf("91") > -1 )

								{
								    Ext.Msg.alert("错误","没有此权限！");
								    return;
								}*/
								
								var record = grid.getStore().getAt(rowIndex);  
				  				guideNo = record.data.guideNo;
				  				var validBit  = record.data.validBit;
				  				
				  				boundSupply(validBit);
								}
							}
						]	
					},{
						text:"胸卡编号查询与生成",
						xtype:'actioncolumn',
						sortable: true,
						width:120,
						align:'center',
						items :[
							{
							text : '胸卡编号查询与生成',
							xtype : 'button',
							tooltip: '胸卡编号查询与生成',
							icon:_appctx+'/images/review.png',
							handler:function(grid, rowIndex, colIndex){
								
							/*	if(roleUser.indexOf("91") > -1 )
								{
								    Ext.Msg.alert("错误","没有此权限！");
								    return;
								}*/
								
								var record = grid.getStore().getAt(rowIndex);  
				  				guideNo = record.data.guideNo;
				  				name = record.data.name;
				  				var validBit  = record.data.validBit;
				  				createChest(validBit);
								}
							}
						]	
					},{
						text:"注销",
						xtype:'actioncolumn',
						sortable: true,
						width:50,
						align:'center',
						items :[
							{
							text : '注销',
							xtype : 'button',
							tooltip: '注销',
							icon:_appctx+'/images/remove.gif',
							handler:function(grid, rowIndex, colIndex){
								var record = grid.getStore().getAt(rowIndex);  
								
								Ext.Msg.confirm("提示","确认要注销该导购信息？",function(button){
									if(button=="yes"){
										sid = record.data.sid;
										guideNo = record.data.guideNo;
										validBit = 0;
										delGuideinfo(sid,guideNo,validBit);
									}
								});
								}
							},
						]	
					},
					{
						text:"恢复",
						xtype:'actioncolumn',
						sortable: true,
						width:50,
						align:'center',
						items :[
							{
							text : '恢复',
							xtype : 'button',
							tooltip: '恢复',
							icon:_appctx+'/images/reset.gif',
							handler:function(grid, rowIndex, colIndex){
								var record = grid.getStore().getAt(rowIndex);  
								
								Ext.Msg.confirm("提示","确认要恢复该导购信息？",function(button){
									if(button=="yes"){
										sid = record.data.sid;
										guideNo = record.data.guideNo;
										validBit = 1;
										delGuideinfo(sid,guideNo,validBit);
									}
								});
								}
							},
						]	
					},{
						text:"删除",
						xtype:'actioncolumn',
						sortable: true,
						width:50,
						align:'center',
						items :[
							{
							text : '删除',
							xtype : 'button',
							tooltip: '删除',
							icon:_appctx+'/images/del.gif',
							handler:function(grid, rowIndex, colIndex){
								var record = grid.getStore().getAt(rowIndex);  
								
								Ext.Msg.confirm("提示","确认要删除该导购信息？",function(button){
									if(button=="yes"){
										sid = record.data.sid;
										guideNo = record.data.guideNo;
										validBit = 0;
										delTrueGuideinfo(sid,guideNo,validBit);
									}
								});
								}
							}
						]	
					}
				],
			store: this.guideInfoStore,  
			columnLines : true,
			selModel: this.sm,
			tbar : [{
				xtype: "buttongroup",
				width:"100%",
				columns: 9,
				items:[
					{
						xtype : "textfield",
						id:"guideNameId",
						fieldLabel:"导购姓名",
						labelAlign:"right",
						labelWidth: 60,
						width : 140
					},{
						xtype : "textfield",
						id:"spellId",
						fieldLabel:"姓名拼音",
						labelAlign:"right",
						labelWidth: 60,
						width : 140
					},{
						xtype : "textfield",
						id:"chestcardNumberId",
						fieldLabel:"胸卡编号",
						labelAlign:"right",
						labelWidth: 60,
						width : 140
					},{
						xtype : "textfield",
						id:"supplyInfoId",
						fieldLabel:"供应商编码",
						labelAlign:"right",
						labelWidth: 80,
						width : 140
					},
					{
						fieldLabel:"是否有效",
						xtype:"combo",
						labelWidth: 70,
						width : 150,
						labelAlign:"right",
						id:"guideinfoValidBit",
						store: validBitCombo,
						editable:false,
						valueField: 'validBitCode',
						displayField:'value',
						hiddenName:'validBitCode',
						triggerAction : 'all',
						name:'validBit',
						mode:'local'
					},
					{
						fieldLabel:"导购状态",
						xtype:"combo",
						id : "guideinfoStatus",
						editable:false,
						labelWidth: 70,
						width : 150,
						labelAlign:"right",
						store: guideStatusCombo,
						valueField: 'guideStatusCode',
						displayField:'value',
						hiddenName:'guideStatusCode',
						triggerAction : 'all',
						name:'guideStatus',
						mode:'local'
					},
					{
						xtype:"combo",
						fieldLabel : '门店',
						labelWidth: 50,
						labelAlign:"right",
						editable:false,
						width : 150,
						id:"shopGuideManageSid",
						store : shopStore,
						valueField: 'sid',
						displayField:'shopName',
						hiddenName:'sid',
						triggerAction : 'all',
						name:'shop',
						mode:'local'
					},
					{
						text:'查找',
						width:80,
						pressed: true,
						ctCls: 'x-btn-over',
						margin : '0 10 0 25',
						handler:function(){
							pointsStore.load({
								params:{
									chestcardNumber : Ext.getCmp('chestcardNumberId').getValue(),
									supplyId : Ext.getCmp('supplyInfoId').getValue(),
									guideName : Ext.getCmp('guideNameId').getValue(),
									guideStatusId : Ext.getCmp('guideinfoStatus').getValue(),
									validBit : Ext.getCmp('guideinfoValidBit').getValue(),
									spell : Ext.getCmp('spellId').getValue(),
									shop : Ext.getCmp('shopGuideManageSid').getValue()
								}
							});
						}
					},
					{
						xtype : 'button',
						text : '导出EXCEL',
						width:80,
						pressed: true,
						ctCls: 'x-btn-over',
						margin : '0 0 0 0',
						handler : function() {
							var guideName =  Ext.getCmp('guideNameId').getValue();
							var spell = Ext.getCmp('spellId').getValue();
							var chestcardNumber =  Ext.getCmp('chestcardNumberId').getValue();
							var supplyId = Ext.getCmp('supplyInfoId').getValue();
							var validBit =  Ext.getCmp('guideinfoValidBit').getValue();
							var guideinfoStatus = Ext.getCmp('guideinfoStatus').getValue();
							var shop = Ext.getCmp('shopGuideManageSid').getValue();
							var appWindow = window.open(_appctx+"/guideinfo/exportGuideinfoToExcel.json?guideName="+guideName+
									"&spell="+spell+"&chestcardNumber="+chestcardNumber+"&validBit="+validBit+
									"&guideinfoStatus="+guideinfoStatus+"&supplyId="+supplyId+"&shop="+shop+"&type=1");
							appWindow.focus();
						}
				},
				{
							text : '手动变价权限',
							xtype : 'button',
							pressed: true,
						    ctCls: 'x-btn-over',
						    margin : '0 10 0 10',
//							tooltip: '手动变价权限',
//							icon:_appctx+'/images/authorize.png',
							handler:function(){
								var str = roleUser;
							/*	var s = str.split(",")[0];
								//卖场主管角色id为32
								if(roleUser.indexOf("91") == -1 && roleUser.indexOf("32") == -1 && s != 1 )
								{
								    Ext.Msg.alert("错误","没有此权限！");
								    return;
								}*/
								
								//获取所有选中行
								 var record = Ext.getCmp('guideInfoGridPanel').getSelectionModel().getSelection();   
	                                if(record.length==0){  
	                                     Ext.MessageBox.show({   
	                                        title:"提示",   
	                                        msg:"请先选择导购!"   
	                                     })  
	                                    return;  
	                                }else{  
	                                    var guideNos = "";   
	                                    var ifValid = false;
	                                    var validNames = "";
	                                    for(var i = 0; i < record.length; i++){   
	                                    	var validBit = record[i].get("validBit");
	                                    	if(0 == validBit){
	                                    		ifValid = true;
	                                    		validNames = validNames + record[i].get("name") + ",";
	                                    	}
	                                        guideNos += record[i].get("guideNo");   
	                                        if(i<record.length-1){   
	                                            guideNos = guideNos + ",";   
	                                        }   
	                                    }
	                                    //无效状态的导购提示
	                                    if(ifValid){
		                                    	Ext.MessageBox.show({   
		                                            title:"提示",   
		                                            msg:validNames+"导购已是无效状态!"   
		                                        }  
		                                    )
		                                    return;
	                                    }
	                                } 
								
								var win = null;
								var form = Ext.create('Ext.form.Panel', {
									height : 310,
									width  : 420,
									layout : 'anchor',
									bodyPadding: '15 15 15 15',
									defaults : {
										anchor : '100%',
										labelAlign : 'right'
									},
									items: [ 
											{
											    xtype: 'container',
											    layout: 'hbox',
											    items: [
													{
							                    		fieldLabel:"权限",
														xtype:"radiogroup",
														width : 300,
														id:'flag',
														items: [
										                        { boxLabel: "开启", name: "flag", inputValue: "1" },
										                        { boxLabel: "关闭", name: "flag", inputValue: "0" }
										                    ],
														name:'flag',
														mode:'local'
													}]
											},
											{
							                	xtype: 'container',
							                    layout: 'hbox',
							                    height:5
							                },
											{
							                    xtype: 'container',
							                    layout: 'hbox',
							                    items: [
													{
														xtype: 'datefield',
														name : 'endtime',
														id : 'endtime',
														labelWidth:100,
														width : 300,
														fieldLabel : '权限截止时间至',
														editable:false,
														allowBlank : false,
														format:'Y-m-d H:i:s'
													}
												]
											}
					                ]
								});
								win = Ext.create('Ext.window.Window', {
									title:"手动变价权限",
									height : 310,
									width  : 420,
									id:'authorizeWindowId',
									plain : true,
									resizable :true,
									items : [ form ],
									buttons : [ {
										text : '重置',
										handler : function() {
										form.getForm().reset();
										}
									}, {
										text : '确定',
										handler : function() {
										
											var flag = Ext.getCmp("flag").getValue(); 
//											var starttime = Ext.getCmp("starttime").getValue(); 
											var endtime = Ext.getCmp("endtime").getValue(); 
											var flagInput = Ext.getCmp("flag").getChecked().inputValue;
											if(null == endtime){
												Ext.Msg.alert('提示',"请选择权限截止日期");
												return;
											}
											Ext.Ajax.request({
												url: _appctx + '/permission/savePermission',
												method:'POST',
												params: { 
													guideNos : guideNos,
													type : 1,             //操作类型,type=1为变价权限操作
//													startTime : starttime,
													endtime : endtime,
													flag: flag,
													userSid : userSid,
													username : username
												},
												success: function(response){
													var result = Ext.decode(response.responseText);
													if(result.success=="true"){
														me.guideInfoStore.reload();
														Ext.Msg.alert('提示',result.obj);
														me.guideInfoStore.reload();
													}else{
														Ext.Msg.alert('错误',result.memo);
													}
												},
												failure: function(){
													Ext.Msg.alert("错误","访问失败！")
												}
											})
										   Ext.getCmp('authorizeWindowId').close();
										}
									}, {
										text : '取消',
										handler : function() {
											win.close();
										}
									} ]
								});
								Ext.getCmp(me.id).add(win);
								win.show();
								}
							},{
								text : '客服退货支付权限',
								xtype : 'button',
								pressed: true,
							    ctCls: 'x-btn-over',
							    margin : '0 10 0 10',
								handler:function(){
									var str = roleUser;
									/*var s = str.split(",")[0];
									//卖场主管角色id测试为32,正式为91
									if(roleUser.indexOf("91") == -1 && roleUser.indexOf("32") == -1 && s != 1 )
									{
									    Ext.Msg.alert("错误","没有此权限！");
									    return;
									}*/
									
									//获取所有选中行
									 var record = Ext.getCmp('guideInfoGridPanel').getSelectionModel().getSelection();   
		                                if(record.length==0){  
		                                     Ext.MessageBox.show({   
		                                        title:"提示",   
		                                        msg:"请先选择导购!"   
		                                     })  
		                                    return;  
		                                }else{  
		                                    var guideNos = "";   
		                                    var ifValid = false;
		                                    var validNames = "";
		                                    for(var i = 0; i < record.length; i++){   
		                                    	var validBit = record[i].get("validBit");
		                                    	if(0 == validBit){
		                                    		ifValid = true;
		                                    		validNames = validNames + record[i].get("name") + ",";
		                                    	}
		                                        guideNos += record[i].get("guideNo");   
		                                        if(i<record.length-1){   
		                                            guideNos = guideNos + ",";   
		                                        }   
		                                    }
		                                    //无效状态的导购提示
		                                    if(ifValid){
			                                    	Ext.MessageBox.show({   
			                                            title:"提示",   
			                                            msg:validNames+"导购已是无效状态!"   
			                                        }  
			                                    )
			                                    return;
		                                    }
		                                } 
									
									var win = null;
									var form = Ext.create('Ext.form.Panel', {
										height : 310,
										width  : 420,
										layout : 'anchor',
										bodyPadding: '15 15 15 15',
										defaults : {
											anchor : '100%',
											labelAlign : 'right'
										},
										items: [ 
												{
												    xtype: 'container',
												    layout: 'hbox',
												    items: [
														{
								                    		fieldLabel:"权限",
															xtype:"radiogroup",
															width : 300,
															id:'kfuflag',
															items: [
											                        { boxLabel: "开启", name: "flag", inputValue: "1" },
											                        { boxLabel: "关闭", name: "flag", inputValue: "0" }
											                    ],
															name:'flag',
															mode:'local'
														}]
												},
												{
								                	xtype: 'container',
								                    layout: 'hbox',
								                    height:5
								                }
						                ]
									});
									win = Ext.create('Ext.window.Window', {
										title:"客服退货支付权限",
										height : 310,
										width  : 420,
										id:'kfuWindowId',
										plain : true,
										resizable :true,
										items : [ form ],
										buttons : [ {
											text : '重置',
											handler : function() {
											form.getForm().reset();
											}
										}, {
											text : '确定',
											handler : function() {
											
												var flag = Ext.getCmp("kfuflag").getValue(); 
												var flagInput = Ext.getCmp("kfuflag").getChecked().inputValue;
												Ext.Ajax.request({
													url: _appctx + '/permission/savePermission',
													method:'POST',
													params: { 
														guideNos : guideNos,
														type : 2,             //操作类型,type=2为客服退货支付权限操作
														flag: flag,
														userSid : userSid,
														username : username
													},
													success: function(response){
														var result = Ext.decode(response.responseText);
														if(result.success=="true"){
															me.guideInfoStore.reload();
															Ext.Msg.alert('提示',result.obj);
															me.guideInfoStore.reload();
														}else{
															Ext.Msg.alert('错误',result.memo);
														}
													},
													failure: function(){
														Ext.Msg.alert("错误","访问失败！")
													}
												})
											   Ext.getCmp('kfuWindowId').close();
											}
										}, {
											text : '取消',
											handler : function() {
												win.close();
											}
										} ]
									});
									Ext.getCmp(me.id).add(win);
									win.show();
									}
								}
				]
			}],
			sortableColumns : false
		});
		
		Ext.getCmp('guideinfoValidBit').select(validBitCombo.getAt(0));
		Ext.getCmp('guideinfoStatus').select(guideStatusCombo.getAt(0));
		
		var pointsStore = this.guideInfoStore;
		
		/**
		 * 修改
		 */
		function updateGuideinfo(record){
			  Ext.create('ShopinDesktop.GuideinfoUpdateWindow',{
				  	record : record
				}).show();
		 }
		
		/**
		 * 导购绑定供应商
		 */
		function boundSupply(validBit){
			  Ext.create('ShopinDesktop.GuideBoundSupplyWindow',{
				    guideNo : guideNo,
				    validBit : validBit
				}).show();
		 }
		
		/**
		 * 生成胸卡编号
		 */
		function createChest(validBit){
			Ext.create('ShopinDesktop.GuideCreastChestcartNumWindow',{
				    guideNo : guideNo,
				    name : name,
				    validBit : validBit
				}).show();
		 }
		
		/**
		 * 删除
		 * @param {Object} sid
		 */
		function delGuideinfo(sid){
			Ext.Ajax.request({
				url : _appctx + '/guideinfo/updateGuidelValidStatus',
				method:'POST',
				params: { 
					sid : sid,
					guideNo : guideNo,
					username : username,
					userSid : userSid,
					validBit : validBit
					},
				success: function(response){
					var result = Ext.decode(response.responseText);
					if(result.success=="true"){
						Ext.Msg.alert('','操作成功！');
						me.guideInfoStore.reload();
					}else{
						Ext.Msg.alert('错误',result.memo);
					}
				},
				failure: function(){
					Ext.Msg.alert('','操作失败，请与管理员联系');
				}
				})
		 }
		
		/**
		 * shanchu del miao
		 */
		function delTrueGuideinfo(sid){
			Ext.Ajax.request({
				url : _appctx + '/guideinfo/delGuidelValidStatus',
				method:'POST',
				params: { 
					sid : sid,
					guideNo : guideNo,
					username : username,
					userSid : userSid,
					validBit : validBit
					},
				success: function(response){
					var result = Ext.decode(response.responseText);
					if(result.success=="true"){
						Ext.Msg.alert('','删除成功！');
						me.guideInfoStore.reload();
					}else{
						Ext.Msg.alert('错误',result.memo);
					}
				},
				failure: function(){
					Ext.Msg.alert('','删除失败，请与管理员联系');
				}
				})
		 }
		
		this.pageBar = Ext.create("Ext.toolbar.Paging", {
			store : this.guideInfoStore,
			displayInfo : true
		});
	}
});
