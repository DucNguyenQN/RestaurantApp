﻿using AutoMapper;
using Microsoft.AspNetCore.Identity;
using Restaurant.API.Data;
using Restaurant.API.Models;
using Restaurant.API.Repository.IRepository;

namespace Restaurant.API.Repository
{
    public class UnitOfWork : IUnitOfWork
    {
        private readonly ApplicationDbContext _db;
        private readonly UserManager<ApplicationUser> _userManager;
        private readonly RoleManager<IdentityRole> _roleManager;
        private readonly IConfiguration _configuration;
        private readonly IMapper _mapper;
        public IMenuItemRepository MenuItem { get; private set; }
        public ICategoryRepository Category { get; private set; }
        public IShoppingCartRepository ShoppingCart { get; private set; }
        public ICartItemRepository CartItem { get; private set; }
        public IOrderHeaderRepository OrderHeader { get; private set; }
        public IOrderDetailRepository OrderDetail { get; private set; }
        public IBookingRepository Booking { get; private set; }
        public IUserRepository User { get; private set; }

        public IAuthRepository Auth { get; private set; }

        public UnitOfWork(ApplicationDbContext db, IConfiguration configuration, UserManager<ApplicationUser> userManager, RoleManager<IdentityRole> roleManager, IMapper mapper)
        {
            _db = db;
            _userManager = userManager;
            _roleManager = roleManager;
            _configuration = configuration;
            _mapper = mapper;

            MenuItem = new MenuItemRepository(_db);
            Category = new CategoryRepository(_db);
            ShoppingCart = new ShoppingCartRepository(_db);
            CartItem = new CartItemRepository(_db);
            OrderHeader = new OrderHeaderRepository(_db);
            OrderDetail = new OrderDetailRepository(_db);
            Booking = new BookingRepository(_db);
            User = new UserRepository(_db);
            Auth = new AuthRepository(_userManager, _roleManager, _configuration, _mapper);
        }

        public async Task SaveAsync()
        {
            await _db.SaveChangesAsync();
        }
    }
}
