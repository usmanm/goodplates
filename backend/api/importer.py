import json
from models import MenuItem, Venue
from django.db import transaction
def load_venue(d):
	try:
		v = Venue.objects.get(locu_id=d["id"])
	except Venue.DoesNotExist:
		v = Venue(locu_id=d["id"],
		          name=d["name"],
		          address=d["address"],
		          locality=d["locality"],
		          region=d["region"],
		          website=d["website"]
		)
		v.save()
	return v

def load_item(d):
	v = load_venue(d["venue"]) 
	try:
		m = MenuItem.objects.get(locu_id=d["id"])
	except MenuItem.DoesNotExist:
		m = MenuItem(locu_id=d["id"],
		             venue=v,
		             title=d["name"],
		             description=d["description"],
		             section=d["section"],
		             price=d["price"]
		)
		m.save()
	return m

@transaction.commit_manually
def import_items(file):
	for item in json.load(open(file)):
		load_item(item)
	transaction.commit()
