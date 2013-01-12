def main():
	from api.importer import import_items
	import_items("../turk/data/menu_item.json")
	from api.rating_importer import import_ratings
	import_ratings("../turk/data/training.csv")
	from ml import ML
	ML.build()

main()
