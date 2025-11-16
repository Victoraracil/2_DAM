using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Text;
using System.Threading.Tasks;

namespace Video_Club
{
    public class Cliente : INotifyPropertyChanged
    {
        // Atributos
        private int id;
        private string usuario = string.Empty;
        private string nombreCompleto = string.Empty;
        private string correoElectronico = string.Empty;
        private int activo;
        private DateTime fechaCreacion = DateTime.Now;
        private DateTime fechaBaja = DateTime.MinValue;

        [Key]
        public int Id
        {
            get { return id; }
            set { id = value; OnPropertyChanged(); }
        }

        [Required, MaxLength(30)]
        public string Usuario
        {
            get { return usuario; }
            set { usuario = value; OnPropertyChanged(); }
        }


        [MaxLength(100)]
        public string NombreCompleto
        {
            get { return nombreCompleto; }
            set { nombreCompleto = value; OnPropertyChanged(); }
        }

        [Required, MaxLength(100), EmailAddress]
        public string CorreoElectronico
        {
            get { return correoElectronico; }
            set { correoElectronico = value; OnPropertyChanged(); }
        }

        [Range(0, 1)]
        public int Activo
        {
            get { return activo; }
            set { activo = value; OnPropertyChanged(); }
        }

        public DateTime FechaCreacion
        {
            get { return fechaCreacion; }
            set { fechaCreacion = value; OnPropertyChanged(); }
        }

        public DateTime FechaBaja
        {
            get { return fechaBaja; }
            set { fechaBaja = value; OnPropertyChanged(); }
        }

        // Relaciones entre tablas

        // Relación 1:N con Películas
        [InverseProperty("Cliente")]
        public virtual ICollection<Pelicula> Peliculas { get; set; } = new List<Pelicula>();

        // Relación N:M con Categorías
        [InverseProperty("Clientes")]
        public virtual ICollection<Categoria> Categorias { get; set; } = new List<Categoria>();



        // Implementación de la interfaz INotifyPropertyChanged

        public event PropertyChangedEventHandler? PropertyChanged;

        protected void OnPropertyChanged([CallerMemberName] string? propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}
