from models import MenuItem, User, Rating

import csv

def import_ratings(f):
	with open(f) as csvfile:
		reader = csv.DictReader(csvfile)
		for row in reader:
			if row["this_dish_is_an_entree_on_a_dinner_menu__as_opposed_to_beverages_appetizers_or_sides"] == "Yes":
				try:
					item = MenuItem.objects.get(locu_id=row["id"])
				except MenuItem.DoesNotExist:
					print "Could not find a menuitem", row["id"]
					continue
				username = row["_worker_id"]
				try:
					user = User.objects.get(username=username)
				except User.DoesNotExist:
					user = User(username=username)
					user.save()
				if row["i_would_like_to_eat_this_dish"] == "Yes":
					value = Rating.LIKE
				else:
					value = Rating.DISLIKE
				r = Rating(user=user, menu_item=item, value=value)
				r.save()
