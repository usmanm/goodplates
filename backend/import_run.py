def run():
	from api.importer import import_items
	print("importing menu items")
	import_items("../turk/data/menu_item.json")
	print("importing ratings")
	from api.rating_importer import import_ratings
	import_ratings("../turk/data/training.csv")
	from ml import ML
	print("rebuilding model")
	ML.build()

run()
