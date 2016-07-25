/**
 * PAD配件管理界面
 */

Ext.define("ShopinDesktop.PadPartsView",{
    extend: 'Ext.window.Window',
    requires: ['Ext.grid.Panel','Ext.data.Store','Ext.data.Model'],
    init : function(){},
    constructor: function (config) {
    	config = config || {
		};
		Ext.apply(this, config);
		this.initComponents();
        this.callParent({}); 
//        this.superclass.constructor.call(this,{});
    },
    initComponents:function(){
    	id:'padPartsView1'
    	
    }
});